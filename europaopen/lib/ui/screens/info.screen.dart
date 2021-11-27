import 'package:flutter/material.dart';

class InfoScreen extends StatelessWidget {
  static const String routeName = '/info';

  const InfoScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Info'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            ExpansionTile(
              title: Text('Regions'),
              subtitle: Text('Current color-coded status'),
              children: [
                ListTile(),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
