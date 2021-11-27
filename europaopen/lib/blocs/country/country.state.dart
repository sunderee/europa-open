import 'package:equatable/equatable.dart';
import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/data/models/countries/country.model.dart';

class CountryState extends Equatable {
  final ShortStatus status;
  final List<CountryModel>? data;
  final String? errorMessage;

  const CountryState._({
    required this.status,
    this.data,
    this.errorMessage,
  });

  factory CountryState.loading() => const CountryState._(
        status: ShortStatus.loading,
      );

  factory CountryState.successful(List<CountryModel> countriesList) =>
      CountryState._(
        status: ShortStatus.successful,
        data: countriesList,
      );

  factory CountryState.failed(String errorMessage) => CountryState._(
        status: ShortStatus.failed,
        errorMessage: errorMessage,
      );

  @override
  List<Object?> get props => [
        status,
        List<CountryModel>.from(data ?? <CountryModel>[]),
        errorMessage,
      ];
}
