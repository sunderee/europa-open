import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/cache.provider.dart';
import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:europaopen/data/repositories/country.repository.dart';
import 'package:hive_flutter/hive_flutter.dart';

class AppDependencies {
  static ApiProvider apiProviderInstance(String baseURL) {
    return ApiProvider(baseURL);
  }

  static CacheProvider<CountryModel> countryCacheProviderInstance() {
    final box = Hive.box<CountryModel>(CacheProvider.countryBoxName);
    return CacheProvider<CountryModel>(box);
  }

  static ICountryRepository countryRepositoryInstance(
    ApiProvider apiProvider,
    CacheProvider<CountryModel> cacheProvider,
  ) {
    return CountryRepository(apiProvider, cacheProvider);
  }

  const AppDependencies._();
}
