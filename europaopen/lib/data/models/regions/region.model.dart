import 'package:equatable/equatable.dart';
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

extension RegionColorExt on String {
  RegionColor fromString() {
    switch (this) {
      case 'darkred':
        return RegionColor.darkRed;
      case 'red':
        return RegionColor.red;
      case 'orange':
        return RegionColor.orange;
      case 'green':
        return RegionColor.green;
      case 'nodata':
        return RegionColor.noData;
      default:
        return RegionColor.noData;
    }
  }
}
