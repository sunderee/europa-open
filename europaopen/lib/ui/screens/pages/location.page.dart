import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:flutter/material.dart';

class LocationPage extends StatelessWidget {
  const LocationPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final imageWidth = MediaQuery.of(context).size.width * 0.55;
    return Center(
      child: ListView(
        shrinkWrap: true,
        children: [
          Image.asset(
            'assets/location.png',
            width: imageWidth,
            height: imageWidth,
          ),
          const SizedBox(height: 24.0),
          Text(
            'Info Per Country',
            style: Typography.englishLike2018.headline6?.copyWith(
              color: ColorTheme.colorProduct,
            ),
            textAlign: TextAlign.center,
          ),
          Text(
            'Fetch Covid-related info for a country of choice',
            style: Typography.englishLike2018.bodyText1,
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16.0),
          FormField(
            builder: (FormFieldState<String> state) {
              return DropdownButtonHideUnderline(
                child: DropdownButton<String>(
                  value: 'Slovenia',
                  items: [
                    DropdownMenuItem<String>(
                      value: 'Slovenia',
                      child: Text('Slovenia'),
                    ),
                  ],
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
