import 'package:europaopen/ui/screens/home.screen.dart';
import 'package:flutter/material.dart';

class AppRouter {
  static const String initialRouter = HomeScreen.routeName;

  static final Map<String, WidgetBuilder> routes = {
    HomeScreen.routeName: (BuildContext context) => const HomeScreen(),
  };

  const AppRouter._();
}
