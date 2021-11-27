import 'dart:convert';
import 'dart:io';

import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/models/domains/indicator.model.dart';
import 'package:europaopen/data/models/rules/rule.model.dart';
import 'package:europaopen/utils/tuple.dart';
import 'package:flutter/foundation.dart';

Future<List<RuleModel>> _rulesForDomainIsolate(
  Triple<ApiProvider, List<IndicatorModel>, String> input,
) async {
  final rulesMap = Map.fromEntries(
    input.second.map((IndicatorModel element) => MapEntry(
          element.indicatorID,
          element.rules,
        )),
  );

  final fullRulesList = <RuleModel>[];

  for (var rulesEntry in rulesMap.entries) {
    final rulesList = [rulesEntry.key, ...rulesEntry.value].join(',');
    final rawRulesResult = await input.first.makeGetRequest(
      'api/covid/v1/eutcdata/data/en/${input.third}/$rulesList',
      headers: {
        HttpHeaders.contentTypeHeader: ContentType.json.toString(),
      },
    );
    final rulesResult = (((json.decode(rawRulesResult) as List<dynamic>).first
            as Map<String, dynamic>)['indicators'] as List<dynamic>)
        .cast<Map<String, dynamic>>();
    final rule = RuleModel.from(
        rulesResult.first,
        rulesResult
            .sublist(1)
            .map(
              (Map<String, dynamic> element) =>
                  RuleModel.from(element, const <RuleModel>[]),
            )
            .toList());
    fullRulesList.add(rule);
  }

  return [];
}

abstract class IRuleRepository {
  Future<List<RuleModel>> rulesForDomain(
    List<IndicatorModel> indicators,
    String countryCode,
  );
}

class RuleRepository implements IRuleRepository {
  final ApiProvider _provider;

  const RuleRepository(
    ApiProvider apiProvider,
  ) : _provider = apiProvider;

  @override
  Future<List<RuleModel>> rulesForDomain(
    List<IndicatorModel> indicators,
    String countryCode,
  ) {
    return compute<Triple<ApiProvider, List<IndicatorModel>, String>,
        List<RuleModel>>(
      _rulesForDomainIsolate,
      Triple(_provider, indicators, countryCode),
    );
  }
}
