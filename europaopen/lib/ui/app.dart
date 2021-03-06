import 'package:europaopen/app.router.dart';
import 'package:europaopen/ui/screens/home.screen.dart';
import 'package:europaopen/ui/themes/app.theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(
        systemNavigationBarContrastEnforced: false,
        statusBarColor: Colors.transparent,
      ),
    );
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: AppTheme.lightTheme,
      initialRoute: AppRouter.initialRouter,
      routes: AppRouter.routes,
    );
  }
}
