import 'package:europaopen/app.router.dart';
import 'package:europaopen/blocs/country/country.cubit.dart';
import 'package:europaopen/blocs/country/country.state.dart';
import 'package:europaopen/blocs/info/info.cubit.dart';
import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/blocs/travel/travel.cubit.dart';
import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:europaopen/ui/screens/pages/location.page.dart';
import 'package:europaopen/ui/screens/pages/travel.page.dart';
import 'package:europaopen/ui/widgets/error_container.widget.dart';
import 'package:europaopen/ui/widgets/loading_container.widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class HomeScreen extends StatelessWidget {
  static const String routeName = '/';

  final CountryCubit countryCubit;
  final InfoCubit infoCubit;
  final TravelCubit travelCubit;

  const HomeScreen({
    Key? key,
    required this.countryCubit,
    required this.infoCubit,
    required this.travelCubit,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [
          IconButton(
            onPressed: () => AppRouter.navigateToAboutScreen(context),
            icon: const Icon(Icons.info_outline),
          ),
        ],
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(16.0),
        child: BlocBuilder<CountryCubit, CountryState>(
          builder: (BuildContext context, CountryState state) {
            if (state.status == ShortStatus.loading) {
              return const Center(
                child: LoadingContainerWidget(),
              );
            } else if (state.status == ShortStatus.successful) {
              final countries = state.data ?? <CountryModel>[];
              countries.sort((CountryModel first, CountryModel second) => first
                  .name
                  .toLowerCase()
                  .compareTo(second.name.toLowerCase()));
              return PageView(
                children: [
                  LocationPage(
                    countries: countries
                        .where((CountryModel element) =>
                            element.direction == CountryDirection.both ||
                            element.direction == CountryDirection.to)
                        .toList(),
                    onCountrySelected: (CountryModel country) {
                      infoCubit.retrieveInfoForCountry(country.code);
                      AppRouter.navigateToInfoScreen(context);
                    },
                  ),
                  TravelPage(
                    countries: countries,
                    onCountriesSelected: (
                      CountryModel staringCountry,
                      CountryModel destinationCountry,
                      CountryModel? transitCountry,
                    ) {
                      if (transitCountry != null) {
                        travelCubit.retrieveTravelInfoWithTransit(
                          staringCountry.code,
                          destinationCountry.code,
                          transitCountry.code,
                        );
                      } else {
                        travelCubit.retrieveTravelInfo(
                          staringCountry.code,
                          destinationCountry.code,
                        );
                      }
                    },
                  ),
                ],
              );
            } else if (state.status == ShortStatus.failed) {
              return Center(
                child: ErrorContainerWidget(
                  errorMessage: state.errorMessage ?? 'Unknown error',
                ),
              );
            } else {
              return const Center(
                child: ErrorContainerWidget(
                  errorMessage: 'Unknown state',
                ),
              );
            }
          },
        ),
      ),
    );
  }
}
