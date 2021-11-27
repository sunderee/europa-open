import 'package:equatable/equatable.dart';
import 'package:europaopen/data/models/domains/indicator.model.dart';
import 'package:meta/meta.dart';

@immutable
class DomainModel extends Equatable {
  final int domainID;
  final String domainName;
  final List<IndicatorModel> indicators;

  const DomainModel._({
    required this.domainID,
    required this.domainName,
    required this.indicators,
  });

  factory DomainModel.from(
    Map<String, dynamic> json,
    List<IndicatorModel> indicators,
  ) =>
      DomainModel._(
        domainID: json['domain_id'] as int,
        domainName: json['domain_name'] as String,
        indicators: indicators,
      );

  @override
  List<Object?> get props => [
        domainID,
        domainName,
        List<IndicatorModel>.from(indicators),
      ];
}
