import 'dart:convert';
import 'dart:io';

import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/models/travel/travel.model.dart';
import 'package:europaopen/utils/tuple.dart';
import 'package:flutter/foundation.dart';

Future<List<TravelModel>> _retrieveTravelInformationIsolate(
  Pair<ApiProvider, String> input,
) async {
  final rawResult = await input.first.makeGetRequest(
    'api/covid/v1/eutcdata/fromto/en/${input.second}',
    headers: {
      HttpHeaders.contentTypeHeader: ContentType.json.toString(),
    },
  );

  final travelList = <TravelModel>[];

  final result =
      (json.decode(rawResult) as List<dynamic>).cast<Map<String, dynamic>>();
  if (result.length == 3) {
    for (var i = 0; i < result.length; i++) {
      final element = result[i];
      travelList.add(TravelModel.from(
          element,
          i == 0
              ? CountryPosition.origin
              : i == 1
                  ? CountryPosition.transient
                  : CountryPosition.destination));
    }
  } else {
    travelList.addAll([
      TravelModel.from(result.first, CountryPosition.origin),
      TravelModel.from(result.last, CountryPosition.destination),
    ]);
  }
  return travelList;
}

abstract class ITravelRepository {
  Future<List<TravelModel>> retrieveTravelInformation(
    String originCountryCode,
    String destinationCountryCode, {
    String? transitCountryCode,
  });
}

class TravelRepository implements ITravelRepository {
  final ApiProvider _provider;

  const TravelRepository(
    ApiProvider apiProvider,
  ) : _provider = apiProvider;

  @override
  Future<List<TravelModel>> retrieveTravelInformation(
    String originCountryCode,
    String destinationCountryCode, {
    String? transitCountryCode,
  }) {
    final destinations = transitCountryCode != null
        ? '$originCountryCode/$destinationCountryCode/$transitCountryCode'
        : '$originCountryCode/$destinationCountryCode';

    return compute<Pair<ApiProvider, String>, List<TravelModel>>(
      _retrieveTravelInformationIsolate,
      Pair(_provider, destinations),
    );
  }
}
