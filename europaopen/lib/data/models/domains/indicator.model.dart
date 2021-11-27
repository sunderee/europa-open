import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

@immutable
class IndicatorModel extends Equatable {
  final int indicatorID;
  final String indicatorName;
  final List<int> rules;

  const IndicatorModel._({
    required this.indicatorID,
    required this.indicatorName,
    required this.rules,
  });

  factory IndicatorModel.from(Map<String, dynamic> json) => IndicatorModel._(
        indicatorID: json['indicator_id'] as int,
        indicatorName: json['indicator_name'] as String,
        rules: (json['rules'] as List<dynamic>).cast<int>(),
      );

  @override
  List<Object?> get props => [
        indicatorID,
        indicatorName,
        List<int>.from(rules),
      ];
}
