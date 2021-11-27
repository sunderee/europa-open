import 'dart:convert';
import 'dart:io';

import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/models/domains/domain.model.dart';
import 'package:europaopen/data/models/domains/indicator.model.dart';
import 'package:flutter/foundation.dart';

Future<List<DomainModel>> _retrieveDomainsIsolate(ApiProvider provider) async {
  final rawDomains = await provider.makeGetRequest(
    'api/covid/v1/eutcdata/domains/en',
    headers: {
      HttpHeaders.contentTypeHeader: ContentType.json.toString(),
    },
  );
  final domainsMap =
      (json.decode(rawDomains) as List<dynamic>).cast<Map<String, dynamic>>();
  final domainIDs = domainsMap
      .map((Map<String, dynamic> element) => element['domain_id'] as int)
      .join(',');

  final rawIndicators = await provider.makeGetRequest(
    'api/covid/v1/eutcdata/indicators/en/$domainIDs',
    headers: {
      HttpHeaders.contentTypeHeader: ContentType.json.toString(),
    },
  );
  final indicatorsMap = (json.decode(rawIndicators) as List<dynamic>)
      .cast<Map<String, dynamic>>();

  return domainsMap.map((Map<String, dynamic> element) {
    final correspondingIndicators = (indicatorsMap.firstWhere(
      (Map<String, dynamic> indicatorElement) =>
          indicatorElement['domain_id'] as int == element['domain_id'] as int,
    )['indicators'] as List<dynamic>)
        .cast<Map<String, dynamic>>()
        .map((Map<String, dynamic> element) => IndicatorModel.from(element))
        .toList();
    return DomainModel.from(element, correspondingIndicators);
  }).toList();
}

abstract class IDomainRepository {
  Future<List<DomainModel>> retrieveDomains();
}

class DomainRepository implements IDomainRepository {
  final ApiProvider _provider;

  const DomainRepository(
    ApiProvider apiProvider,
  ) : _provider = apiProvider;

  @override
  Future<List<DomainModel>> retrieveDomains() async {
    return compute<ApiProvider, List<DomainModel>>(
      _retrieveDomainsIsolate,
      _provider,
    );
  }
}
