import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:flutter/material.dart';

class AppTheme {
  static final ThemeData lightTheme = ThemeData(
    brightness: Brightness.light,
    primaryColor: ColorTheme.colorProduct,
    scaffoldBackgroundColor: ColorTheme.colorBackgroundLight,
    appBarTheme: const AppBarTheme(
      centerTitle: true,
      elevation: 0.0,
      color: ColorTheme.colorBackgroundLight,
      titleTextStyle: TextStyle(
        color: Colors.black87,
        fontSize: 20.0,
        fontWeight: FontWeight.w500,
      ),
      iconTheme: IconThemeData(
        color: Colors.black87,
      ),
    ),
  );

  const AppTheme._();
}
