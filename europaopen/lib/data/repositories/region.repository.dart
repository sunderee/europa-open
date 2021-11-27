import 'dart:convert';
import 'dart:io';

import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/models/regions/region.model.dart';
import 'package:flutter/foundation.dart';

Future<List<RegionModel>> _retrieveRegionsDataIsolate(
  ApiProvider provider,
) async {
  final rawResult = await provider.makeGetRequest(
    'api/covid/v1/eutcdata/regions/en',
    headers: {
      HttpHeaders.contentTypeHeader: ContentType.json.toString(),
    },
  );
  return (json.decode(rawResult) as List<dynamic>)
      .cast<Map<String, dynamic>>()
      .map((Map<String, dynamic> element) => RegionModel.from(element))
      .toList();
}

abstract class IRegionRepository {
  Future<List<RegionModel>> retrieveRegionsData();
}

class RegionRepository implements IRegionRepository {
  final ApiProvider _provider;

  const RegionRepository(
    ApiProvider apiProvider,
  ) : _provider = apiProvider;

  @override
  Future<List<RegionModel>> retrieveRegionsData() async {
    return compute<ApiProvider, List<RegionModel>>(
      _retrieveRegionsDataIsolate,
      _provider,
    );
  }
}
