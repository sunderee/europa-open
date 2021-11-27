import 'package:europaopen/blocs/country/country.state.dart';
import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/repositories/country.repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class CountryCubit extends Cubit<CountryState> {
  final ICountryRepository _repository;

  CountryCubit(
    ICountryRepository countryRepository,
  )   : _repository = countryRepository,
        super(CountryState.loading());

  Future<void> requestCountries() async {
    emit(CountryState.loading());
    try {
      final countries = await _repository.retrieveAllCountries();
      emit(CountryState.successful(countries));
    } on ApiException catch (exception) {
      emit(CountryState.failed(exception.toString()));
    } catch (_) {
      emit(CountryState.failed('Unknown error'));
    }
  }
}
