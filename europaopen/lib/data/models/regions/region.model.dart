import 'package:equatable/equatable.dart';
import 'package:europaopen/utils/extensions/string.ext.dart';
import 'package:meta/meta.dart';

@immutable
class RegionModel extends Equatable {
  final String isoCode;
  final String code;
  final String name;
  final RegionColor color;

  const RegionModel._({
    required this.isoCode,
    required this.code,
    required this.name,
    required this.color,
  });

  factory RegionModel.from(Map<String, dynamic> json) => RegionModel._(
        isoCode: json['ISO3'] as String,
        code: json['code'] as String,
        name: json['name'] as String,
        color: (json['colour'] as String).fromString(),
      );

  @override
  List<Object?> get props => [
        isoCode,
        code,
        name,
        color,
      ];
}

enum RegionColor { darkRed, red, orange, green, noData }
