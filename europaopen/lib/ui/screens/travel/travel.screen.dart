import 'package:europaopen/blocs/status.dart';
import 'package:europaopen/blocs/travel/travel.cubit.dart';
import 'package:europaopen/blocs/travel/travel.state.dart';
import 'package:europaopen/data/models/travel/travel.model.dart';
import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:europaopen/ui/widgets/error_container.widget.dart';
import 'package:europaopen/ui/widgets/loading_container.widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:html/dom.dart' as dom;
import 'package:url_launcher/url_launcher.dart';

class TravelScreen extends StatelessWidget {
  static const String routeName = '/travel';

  const TravelScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Travel itinerary'),
      ),
      body: BlocBuilder<TravelCubit, TravelState>(
        builder: (BuildContext context, TravelState state) {
          if (state.status == ShortStatus.loading) {
            return const Center(
              child: LoadingContainerWidget(),
            );
          } else if (state.status == ShortStatus.successful) {
            final travelList = state.data ?? <TravelModel>[];
            return SafeArea(
              minimum: const EdgeInsets.symmetric(horizontal: 16.0),
              child: ListView.builder(
                itemCount: travelList.length,
                itemBuilder: (BuildContext context, int index) {
                  final travelModel = travelList[index];
                  return ListTile(
                    contentPadding: EdgeInsets.zero,
                    title: Column(
                      mainAxisSize: MainAxisSize.min,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          travelModel.countryCode,
                          style: Typography.englishLike2018.subtitle1?.copyWith(
                            color: ColorTheme.colorProduct,
                          ),
                        ),
                        Text(
                          travelModel.title,
                          style: Typography.englishLike2018.headline6?.copyWith(
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                      ],
                    ),
                    subtitle: Html(
                      data: travelModel.comment,
                      onLinkTap: _onLinkTapListener,
                    ),
                  );
                },
              ),
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
    );
  }

  Future<void> _onLinkTapListener(
    String? url,
    RenderContext context,
    Map<String, String> arguments,
    dom.Element? element,
  ) async {
    if (url != null) {
      if (await canLaunch(url)) {
        await launch(url);
      }
    }
  }
}
