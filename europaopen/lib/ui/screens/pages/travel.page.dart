import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:flutter/material.dart';

class TravelPage extends StatefulWidget {
  final List<CountryModel> countries;

  const TravelPage({
    Key? key,
    required this.countries,
  }) : super(key: key);

  @override
  State<TravelPage> createState() => _TravelPageState();
}

class _TravelPageState extends State<TravelPage> {
  CountryModel? _startingCountry;
  CountryModel? _destinationCountry;
  CountryModel? _transitCountry;

  @override
  Widget build(BuildContext context) {
    final imageWidth = MediaQuery.of(context).size.width * 0.55;
    return Center(
      child: ListView(
        shrinkWrap: true,
        children: [
          Image.asset(
            'assets/travel.png',
            width: imageWidth,
            height: imageWidth,
          ),
          const SizedBox(height: 24.0),
          Text(
            'Travel plans',
            style: Typography.englishLike2018.headline6?.copyWith(
              color: ColorTheme.colorProduct,
            ),
            textAlign: TextAlign.center,
          ),
          Text(
            'Make sure you\'re aware of restrictions before embarking on a journey',
            style: Typography.englishLike2018.bodyText1,
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16.0),
          FormField(
            builder: (FormFieldState<CountryModel> state) {
              return DropdownButtonHideUnderline(
                child: DropdownButton<CountryModel>(
                  value: _startingCountry,
                  items: widget.countries
                      .where((CountryModel element) =>
                          element.direction == CountryDirection.from ||
                          element.direction == CountryDirection.both)
                      .map(
                        (CountryModel country) =>
                            DropdownMenuItem<CountryModel>(
                          value: country,
                          child: Text(country.name),
                        ),
                      )
                      .toList(),
                  hint: const Text('Originating country'),
                  onChanged: (CountryModel? newSelectedCountry) {
                    if (newSelectedCountry != null) {
                      setState(() => _startingCountry = newSelectedCountry);
                    }
                  },
                ),
              );
            },
          ),
          FormField(
            builder: (FormFieldState<CountryModel> state) {
              return DropdownButtonHideUnderline(
                child: DropdownButton<CountryModel>(
                  value: _destinationCountry,
                  items: widget.countries
                      .where((CountryModel element) =>
                          element.direction == CountryDirection.to ||
                          element.direction == CountryDirection.both)
                      .map(
                        (CountryModel country) =>
                            DropdownMenuItem<CountryModel>(
                          value: country,
                          child: Text(country.name),
                        ),
                      )
                      .toList(),
                  hint: const Text('Destination country'),
                  onChanged: (CountryModel? newSelectedCountry) {
                    if (newSelectedCountry != null) {
                      setState(() => _destinationCountry = newSelectedCountry);
                    }
                  },
                ),
              );
            },
          ),
          FormField(
            builder: (FormFieldState<CountryModel> state) {
              return DropdownButtonHideUnderline(
                child: DropdownButton<CountryModel>(
                  value: _transitCountry,
                  items: widget.countries
                      .where((CountryModel element) =>
                          element.direction == CountryDirection.to ||
                          element.direction == CountryDirection.both)
                      .map(
                        (CountryModel country) =>
                            DropdownMenuItem<CountryModel>(
                          value: country,
                          child: Text(country.name),
                        ),
                      )
                      .toList(),
                  hint: const Text('(Optional) transient country'),
                  onChanged: (CountryModel? newSelectedCountry) {
                    if (newSelectedCountry != null) {
                      setState(() => _transitCountry = newSelectedCountry);
                    }
                  },
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
