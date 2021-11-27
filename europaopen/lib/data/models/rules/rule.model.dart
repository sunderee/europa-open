import 'package:equatable/equatable.dart';

class RuleModel extends Equatable {
  final int domainID;
  final String title;
  final String comment;
  final List<RuleModel> additionalRules;

  const RuleModel._({
    required this.domainID,
    required this.title,
    required this.comment,
    required this.additionalRules,
  });

  factory RuleModel.from(
    Map<String, dynamic> json,
    List<RuleModel> additionalRules,
  ) =>
      RuleModel._(
        domainID: json['domain_id'] as int,
        title: json['indicator_name'] as String,
        comment: json['comment'] as String,
        additionalRules: additionalRules,
      );

  @override
  List<Object?> get props => [
        domainID,
        title,
        comment,
        List<RuleModel>.from(additionalRules),
      ];
}
