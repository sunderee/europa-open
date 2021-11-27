import 'package:hive_flutter/hive_flutter.dart';

class CacheProvider<T> {
  static const String countryBoxName = 'countries_box';

  final Box<T> boxInstance;

  const CacheProvider(this.boxInstance);

  Future<int> insert(T object) async {
    return boxInstance.add(object);
  }

  Iterable<T> readAll() {
    return boxInstance.values;
  }

  bool isEmpty() {
    return boxInstance.isEmpty;
  }
}
