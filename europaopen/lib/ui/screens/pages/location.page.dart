import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:europaopen/utils/extensions/context.ext.dart';
import 'package:flutter/material.dart';

class LocationPage extends StatefulWidget {
  final List<CountryModel> countries;
  final Function(CountryModel) onCountrySelected;

  const LocationPage({
    Key? key,
    required this.countries,
    required this.onCountrySelected,
  }) : super(key: key);

  @override
  State<LocationPage> createState() => _LocationPageState();
}

class _LocationPageState extends State<LocationPage> {
  CountryModel? _selectedCountry;

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
          const SizedBox(height: 4.0),
          Text(
            'Fetch Covid-related info for a country of choice',
            style: Typography.englishLike2018.bodyText1,
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16.0),
          FormField(
            builder: (FormFieldState<CountryModel> state) {
              return DropdownButtonHideUnderline(
                child: DropdownButton<CountryModel>(
                  isDense: true,
                  value: _selectedCountry,
                  items: widget.countries
                      .map(
                        (CountryModel country) =>
                            DropdownMenuItem<CountryModel>(
                          value: country,
                          child: Text(country.name),
                        ),
                      )
                      .toList(),
                  hint: const Text('Select a country'),
                  onChanged: (CountryModel? newSelectedCountry) {
                    if (newSelectedCountry != null) {
                      setState(() => _selectedCountry = newSelectedCountry);
                    }
                  },
                ),
              );
            },
          ),
          const SizedBox(height: 16.0),
          MaterialButton(
            color: ColorTheme.colorProduct,
            child: Text(
              'Continue'.toUpperCase(),
              style: const TextStyle(
                color: Colors.white,
              ),
            ),
            onPressed: () {
              if (_selectedCountry != null) {
                widget.onCountrySelected(_selectedCountry!);
                return;
              }
              context.displaySnackBar('Select a country');
            },
          ),
        ],
      ),
    );
  }
}
