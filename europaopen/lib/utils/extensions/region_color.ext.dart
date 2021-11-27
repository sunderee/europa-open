import 'package:europaopen/data/models/regions/region.model.dart';

extension RegionColorExt on RegionColor {
  String toColorString() {
    switch (this) {
      case RegionColor.darkRed:
        return 'DARK RED';
      case RegionColor.red:
        return 'RED';
      case RegionColor.orange:
        return 'ORANGE';
      case RegionColor.green:
        return 'GREEN';
      case RegionColor.noData:
        return 'NO DATA';
      default:
        return 'NO DATA';
    }
  }
}
