import 'package:europaopen/blocs/country/country.cubit.dart';
import 'package:europaopen/blocs/info/info.cubit.dart';
import 'package:europaopen/ui/screens/home.screen.dart';
import 'package:europaopen/ui/screens/info.screen.dart';
import 'package:europaopen/ui/screens/info_details.screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class AppRouter {
  static const String initialRouter = HomeScreen.routeName;

  static final Map<String, WidgetBuilder> routes = {
    HomeScreen.routeName: (BuildContext context) => HomeScreen(
          countryCubit: BlocProvider.of<CountryCubit>(context)
            ..requestCountries(),
          infoCubit: BlocProvider.of<InfoCubit>(context),
        ),
    InfoScreen.routeName: (BuildContext context) => const InfoScreen(),
    InfoDetailsScreen.routeName: (BuildContext context) =>
        const InfoDetailsScreen(),
  };

  static void navigateToInfoScreen(BuildContext context) {
    Navigator.pushNamed(context, InfoScreen.routeName);
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

  const AppRouter._();
}
