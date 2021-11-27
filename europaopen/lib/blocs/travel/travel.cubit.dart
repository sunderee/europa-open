import 'package:europaopen/blocs/travel/travel.state.dart';
import 'package:europaopen/data/api.provider.dart';
import 'package:europaopen/data/repositories/travel.repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class TravelCubit extends Cubit<TravelState> {
  final ITravelRepository _repository;

  TravelCubit(
    ITravelRepository travelRepository,
  )   : _repository = travelRepository,
        super(TravelState.loading());

  Future<void> retrieveTravelInfo(
    String start,
    String destination,
  ) async {
    emit(TravelState.loading());
    try {
      final travelInfo = await _repository.retrieveTravelInformation(
        start,
        destination,
      );
      emit(TravelState.successful(travelInfo));
    } on ApiException catch (exception) {
      emit(TravelState.failed(exception.toString()));
    } catch (_) {
      emit(TravelState.failed('Unknown error'));
    }
  }

  Future<void> retrieveTravelInfoWithTransit(
    String start,
    String destination,
    String transit,
  ) async {
    emit(TravelState.loading());
    try {
      final travelInfo = await _repository.retrieveTravelInformation(
        start,
        destination,
        transitCountryCode: transit,
      );
      emit(TravelState.successful(travelInfo));
    } on ApiException catch (exception) {
      emit(TravelState.failed(exception.toString()));
    } catch (_) {
      emit(TravelState.failed('Unknown error'));
    }
  }
}
