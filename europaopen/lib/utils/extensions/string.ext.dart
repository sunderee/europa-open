import 'package:europaopen/data/models/regions/region.model.dart';

extension StringExt on String {
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
