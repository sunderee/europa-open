import 'package:europaopen/blocs/info/info.state.dart';
import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/models/domains/domain.model.dart';
import 'package:europaopen/data/models/regions/region.model.dart';
import 'package:europaopen/data/repositories/domain.repository.dart';
import 'package:europaopen/data/repositories/region.repository.dart';
import 'package:europaopen/data/repositories/rule.repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class InfoCubit extends Cubit<InfoState> {
  final IRegionRepository _regionRepository;
  final IDomainRepository _domainRepository;
  final IRuleRepository _ruleRepository;

  InfoCubit(
    IRegionRepository regionRepository,
    IDomainRepository domainRepository,
    IRuleRepository ruleRepository,
  )   : _regionRepository = regionRepository,
        _domainRepository = domainRepository,
        _ruleRepository = ruleRepository,
        super(InfoState.loading());

  Future<void> retrieveInfoForCountry(String countryCode) async {
    emit(InfoState.loading());
    try {
      final regions = (await _regionRepository.retrieveRegionsData())
          .where((RegionModel element) => element.isoCode == countryCode)
          .toList();

      final domains = await _domainRepository.retrieveDomains();
      final domainRules = await _ruleRepository.rulesForDomain(
        domains.expand((DomainModel element) => element.indicators).toList(),
        countryCode,
      );
      emit(InfoState.successful(regions, domains, domainRules));
    } on ApiException catch (exception) {
      emit(InfoState.failed(exception.toString()));
    }
    // } catch (_) {
    //   emit(InfoState.failed('Unknown error'));
    // }
  }
}
