import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

@immutable
class TravelModel extends Equatable {
  final String countryCode;
  final String title;
  final String comment;
  final CountryPosition position;

  const TravelModel._({
    required this.countryCode,
    required this.title,
    required this.comment,
    required this.position,
  });

  factory TravelModel.from(
    Map<String, dynamic> json,
    CountryPosition position,
  ) =>
      TravelModel._(
        countryCode: json['nutscode'] as String,
        title: ((json['indicators'] as List<dynamic>)
            .cast<Map<String, dynamic>>()
            .first)['indicator_name'] as String,
        comment: ((json['indicators'] as List<dynamic>)
            .cast<Map<String, dynamic>>()
            .first)['comment'] as String,
        position: position,
      );

  @override
  List<Object?> get props => [
        countryCode,
        title,
        comment,
        position,
      ];
}

enum CountryPosition { origin, destination, transient }
