import 'package:hive/hive.dart';
import 'package:meta/meta.dart';

@HiveType(typeId: 1)
@immutable
class CountryModel {
  @HiveField(0)
  final String code;

  @HiveField(1)
  final String name;

  @HiveField(2)
  final CountryDirection direction;

  const CountryModel(
    this.code,
    this.name,
    this.direction,
  );

  factory CountryModel.from(
    Map<String, dynamic> json,
    CountryDirection direction,
  ) =>
      CountryModel(
        json['ISO3'] as String,
        json['name'] as String,
        direction,
      );
}

@HiveType(typeId: 2)
enum CountryDirection {
  @HiveField(0)
  from,

  @HiveField(1)
  to,

  @HiveField(2)
  both
}
