import 'package:meta/meta.dart';

@immutable
@sealed
class Triple<A, B, C> {
  final A first;
  final B second;
  final C third;

  @literal
  const Triple(
    this.first,
    this.second,
    this.third,
  );
}
