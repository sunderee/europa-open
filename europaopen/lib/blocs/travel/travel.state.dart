import 'package:equatable/equatable.dart';
import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/data/models/travel/travel.model.dart';

class TravelState extends Equatable {
  final ShortStatus status;
  final List<TravelModel>? data;
  final String? errorMessage;

  const TravelState._({
    required this.status,
    this.data,
    this.errorMessage,
  });

  factory TravelState.loading() => const TravelState._(
        status: ShortStatus.loading,
      );

  factory TravelState.successful(List<TravelModel> travelList) => TravelState._(
        status: ShortStatus.successful,
        data: travelList,
      );

  factory TravelState.failed(String errorMessage) => TravelState._(
        status: ShortStatus.failed,
        errorMessage: errorMessage,
      );

  @override
  List<Object?> get props => [
        status,
        List<TravelModel>.from(data ?? <TravelModel>[]),
        errorMessage,
      ];
}
