import 'package:europaopen/blocs/country/country.cubit.dart';
import 'package:europaopen/blocs/country/country.state.dart';
import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/data/models/countries/country.model.dart';
import 'package:europaopen/ui/screens/pages/location.page.dart';
import 'package:europaopen/ui/widgets/error_container.widget.dart';
import 'package:europaopen/ui/widgets/loading_container.widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class HomeScreen extends StatelessWidget {
  static const String routeName = '/';

  final CountryCubit countryCubit;

  const HomeScreen({
    Key? key,
    required this.countryCubit,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        minimum: const EdgeInsets.all(16.0),
        child: BlocBuilder<CountryCubit, CountryState>(
          builder: (BuildContext context, CountryState state) {
            if (state.status == ShortStatus.loading) {
              return const LoadingContainerWidget();
            } else if (state.status == ShortStatus.successful) {
              return PageView(
                children: [
                  LocationPage(
                    countries: state.data ?? <CountryModel>[],
                  ),
                ],
              );
            } else if (state.status == ShortStatus.failed) {
              return ErrorContainerWidget(
                errorMessage: state.errorMessage ?? 'Unknown error',
              );
            } else {
              return const ErrorContainerWidget(
                errorMessage: 'Unknown state',
              );
            }
          },
        ),
      ),
    );
  }
}
