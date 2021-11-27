import 'package:europaopen/app.router.dart';
import 'package:europaopen/blocs/info/info.cubit.dart';
import 'package:europaopen/blocs/info/info.state.dart';
import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/data/models/domains/domain.model.dart';
import 'package:europaopen/data/models/regions/region.model.dart';
import 'package:europaopen/data/models/rules/rule.model.dart';
import 'package:europaopen/ui/screens/info_details.screen.dart';
import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:europaopen/ui/widgets/error_container.widget.dart';
import 'package:europaopen/ui/widgets/loading_container.widget.dart';
import 'package:europaopen/utils/extensions/region_color.ext.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class InfoScreen extends StatelessWidget {
  static const String routeName = '/info';

  const InfoScreen({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Info'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.symmetric(horizontal: 16.0),
        child: BlocBuilder<InfoCubit, InfoState>(
          builder: (BuildContext context, InfoState state) {
            if (state.status == ShortStatus.loading) {
              return const Center(
                child: LoadingContainerWidget(),
              );
            } else if (state.status == ShortStatus.successful) {
              return ListView(
                children: [
                  Theme(
                    data: ThemeData().copyWith(
                      dividerColor: Colors.transparent,
                      colorScheme: const ColorScheme.light().copyWith(
                        primary: ColorTheme.colorProduct,
                      ),
                    ),
                    child: ExpansionTile(
                      tilePadding: EdgeInsets.zero,
                      title: const Text('Regions'),
                      subtitle: const Text('Current color-coded status'),
                      children: state.regionsData
                              ?.map((RegionModel region) => ListTile(
                                    title: Text(region.name),
                                    subtitle:
                                        Text(region.color.toColorString()),
                                  ))
                              .toList() ??
                          [
                            const SizedBox(),
                          ],
                    ),
                  ),
                  const Divider(),
                  const SizedBox(height: 16.0),
                  ...state.domainsData?.map((DomainModel element) => ListTile(
                            contentPadding: EdgeInsets.zero,
                            title: Text(element.domainName),
                            trailing: IconButton(
                              onPressed: () {
                                AppRouter.navigateToInfoDetailsScreen(
                                  context,
                                  InfoDetailsScreenArguments(
                                    element.domainID,
                                    state.domainRulesData ?? <RuleModel>[],
                                  ),
                                );
                              },
                              icon: const Icon(
                                Icons.arrow_forward_ios,
                                size: 20.0,
                              ),
                            ),
                          )) ??
                      [
                        const SizedBox(),
                      ],
                ],
              );
            } else if (state.status == ShortStatus.failed) {
              return Center(
                child: ErrorContainerWidget(
                  errorMessage: state.errorMessage ?? 'Unknown error',
                ),
              );
            } else {
              return const Center(
                child: ErrorContainerWidget(
                  errorMessage: 'Unknown state',
                ),
              );
            }
          },
        ),
      ),
    );
  }
}
