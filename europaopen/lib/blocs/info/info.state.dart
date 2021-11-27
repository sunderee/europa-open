import 'package:equatable/equatable.dart';
import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/data/models/domains/domain.model.dart';
import 'package:europaopen/data/models/regions/region.model.dart';
import 'package:europaopen/data/models/rules/rule.model.dart';

class InfoState extends Equatable {
  final ShortStatus status;
  final List<RegionModel>? regionsData;
  final List<DomainModel>? domainsData;
  final List<RuleModel>? domainRulesData;
  final String? errorMessage;

  const InfoState._({
    required this.status,
    this.regionsData,
    this.domainsData,
    this.domainRulesData,
    this.errorMessage,
  });

  factory InfoState.loading() => const InfoState._(
        status: ShortStatus.loading,
      );

  factory InfoState.successful(
    List<RegionModel> regions,
    List<DomainModel> domains,
    List<RuleModel> domainRules,
  ) =>
      InfoState._(
        status: ShortStatus.successful,
        regionsData: regions,
        domainsData: domains,
        domainRulesData: domainRules,
      );

  factory InfoState.failed(String errorMessage) => InfoState._(
        status: ShortStatus.failed,
        errorMessage: errorMessage,
      );

  @override
  List<Object?> get props => [
        status,
        List<RegionModel>.from(regionsData ?? <RegionModel>[]),
        List<DomainModel>.from(domainsData ?? <DomainModel>[]),
        List<RuleModel>.from(domainRulesData ?? <RuleModel>[]),
        errorMessage
      ];
}
