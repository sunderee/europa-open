import 'package:europaopen/blocs/country/country.cubit.dart';
import 'package:europaopen/blocs/info/info.cubit.dart';
import 'package:europaopen/blocs/travel/travel.cubit.dart';
import 'package:europaopen/ui/screens/about.screen.dart';
import 'package:europaopen/ui/screens/home.screen.dart';
import 'package:europaopen/ui/screens/info/info.screen.dart';
import 'package:europaopen/ui/screens/info/info_details.screen.dart';
import 'package:europaopen/ui/screens/travel/travel.screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class AppRouter {
  static const String initialRouter = HomeScreen.routeName;

  static final Map<String, WidgetBuilder> routes = {
    HomeScreen.routeName: (BuildContext context) => HomeScreen(
          countryCubit: BlocProvider.of<CountryCubit>(context)
            ..requestCountries(),
          infoCubit: BlocProvider.of<InfoCubit>(context),
          travelCubit: BlocProvider.of<TravelCubit>(context),
        ),
    AboutScreen.routeName: (BuildContext context) => const AboutScreen(),
    InfoScreen.routeName: (BuildContext context) => const InfoScreen(),
    InfoDetailsScreen.routeName: (BuildContext context) =>
        const InfoDetailsScreen(),
    TravelScreen.routeName: (BuildContext context) => const TravelScreen(),
  };

  static void navigateToAboutScreen(BuildContext context) {
    Navigator.pushNamed(context, AboutScreen.routeName);
  }

  static void navigateToInfoScreen(BuildContext context) {
    Navigator.pushNamed(
      context,
      InfoScreen.routeName,
    );
  }

  static void navigateToInfoDetailsScreen(
    BuildContext context,
    InfoDetailsScreenArguments arguments,
  ) {
    Navigator.pushNamed(
      context,
      InfoDetailsScreen.routeName,
      arguments: arguments,
    );
  }

  static void navigateToTravelScreen(BuildContext context) {
    Navigator.pushNamed(
      context,
      TravelScreen.routeName,
    );
  }

  const AppRouter._();
}
