import 'package:flutter/material.dart';

extension BuildContextExt on BuildContext {
  void displaySnackBar(String text) {
    ScaffoldMessenger.of(this).showSnackBar(SnackBar(content: Text(text)));
  }
}
