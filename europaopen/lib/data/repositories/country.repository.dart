import 'dart:convert';
import 'dart:io';

import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/cache.provider.dart';
import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:flutter/foundation.dart';

Future<List<CountryModel>> _retrieveCountriesIsolate(
  ApiProvider provider,
) async {
  final rawFromCountries = await provider.makeGetRequest(
    'api/covid/v1/eutcdata/countries/en/from',
    headers: {
      HttpHeaders.contentTypeHeader: ContentType.json.toString(),
    },
  );
  final rawToCountries = await provider.makeGetRequest(
    'api/covid/v1/eutcdata/countries/en/to',
    headers: {
      HttpHeaders.contentTypeHeader: ContentType.json.toString(),
    },
  );

  final fromCountriesMap = (json.decode(rawFromCountries) as List<dynamic>)
      .cast<Map<String, dynamic>>();
  final toCountriesMap = (json.decode(rawToCountries) as List<dynamic>)
      .cast<Map<String, dynamic>>();
  final bothCountriesMap = <Map<String, dynamic>>[];

  for (var element in fromCountriesMap) {
    if (toCountriesMap.contains(element)) {
      bothCountriesMap.add(element);
    }
  }

  for (var element in bothCountriesMap) {
    if (fromCountriesMap.contains(element)) {
      fromCountriesMap.remove(element);
    }

    if (toCountriesMap.contains(element)) {
      toCountriesMap.remove(element);
    }
  }

  return [
    ...fromCountriesMap.map((Map<String, dynamic> element) =>
        CountryModel.from(element, CountryDirection.from)),
    ...toCountriesMap.map((Map<String, dynamic> element) =>
        CountryModel.from(element, CountryDirection.to)),
    ...bothCountriesMap.map((Map<String, dynamic> element) =>
        CountryModel.from(element, CountryDirection.both)),
  ];
}

abstract class ICountryRepository {
  Future<List<CountryModel>> retrieveAllCountries();
}

class CountryRepository implements ICountryRepository {
  final ApiProvider _provider;
  final CacheProvider<CountryModel> _cacheProvider;

  const CountryRepository(
    ApiProvider apiProvider,
    CacheProvider<CountryModel> cacheProvider,
  )   : _provider = apiProvider,
        _cacheProvider = cacheProvider;

  @override
  Future<List<CountryModel>> retrieveAllCountries() async {
    if (_cacheProvider.isEmpty()) {
      final countries = await compute<ApiProvider, List<CountryModel>>(
        _retrieveCountriesIsolate,
        _provider,
      );
      for (var element in countries) {
        await _cacheProvider.insert(element);
      }

      return countries;
    }

    return _cacheProvider.readAll().toList();
  }
}
