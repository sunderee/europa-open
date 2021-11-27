import 'package:europaopen/data/models/rules/rule.model.dart';
import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:europaopen/ui/widgets/error_container.widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:html/dom.dart' as dom;
import 'package:meta/meta.dart';
import 'package:url_launcher/url_launcher.dart';

@immutable
class InfoDetailsScreenArguments {
  final int domainID;
  final List<RuleModel> rules;

  const InfoDetailsScreenArguments(this.domainID, this.rules);
}

class InfoDetailsScreen extends StatelessWidget {
  static const String routeName = '/info/details';

  const InfoDetailsScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final arguments = ModalRoute.of(context)?.settings.arguments
        as InfoDetailsScreenArguments?;
    if (arguments == null) {
      return const Center(
        child: ErrorContainerWidget(
          errorMessage: 'Internal application error',
        ),
      );
    }
    final domainRules = arguments.rules
        .where((RuleModel element) => element.domainID == arguments.domainID)
        .toList();

    return Scaffold(
      appBar: AppBar(
        title: const Text('Details'),
      ),
      body: ListView.separated(
        itemCount: domainRules.length,
        separatorBuilder: (_, __) => const Divider(),
        itemBuilder: (BuildContext context, int index) {
          final rule = domainRules[index];
          if (rule.additionalRules.isNotEmpty) {
            return Theme(
              data: ThemeData().copyWith(
                dividerColor: Colors.transparent,
                colorScheme: const ColorScheme.light().copyWith(
                  primary: ColorTheme.colorProduct,
                ),
              ),
              child: ExpansionTile(
                title: Text(
                  rule.title,
                  style: Typography.englishLike2018.headline6?.copyWith(
                    fontWeight: FontWeight.w600,
                  ),
                ),
                subtitle: Html(
                  data: rule.comment,
                  onLinkTap: _onLinkTapListener,
                ),
                children: rule.additionalRules
                    .map(
                      (RuleModel element) => ListTile(
                        title: Text(element.title),
                        subtitle: Html(
                          data: element.comment,
                          onLinkTap: _onLinkTapListener,
                        ),
                      ),
                    )
                    .toList(),
              ),
            );
          } else {
            return ListTile(
              title: Text(
                rule.title,
                style: Typography.englishLike2018.headline6?.copyWith(
                  fontWeight: FontWeight.w600,
                ),
              ),
              subtitle: Html(
                data: rule.comment,
                onLinkTap: _onLinkTapListener,
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
