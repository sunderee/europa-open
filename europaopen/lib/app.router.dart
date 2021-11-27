import 'package:europaopen/blocs/country/country.cubit.dart';
import 'package:europaopen/ui/screens/home.screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class AppRouter {
  static const String initialRouter = HomeScreen.routeName;

  static final Map<String, WidgetBuilder> routes = {
    HomeScreen.routeName: (BuildContext context) => HomeScreen(
          countryCubit: BlocProvider.of<CountryCubit>(context)
            ..requestCountries(),
        ),
  };

  const AppRouter._();
}
