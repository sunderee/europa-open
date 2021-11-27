import 'package:europaopen/ui/themes/color.theme.dart';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

class AboutScreen extends StatelessWidget {
  static const String routeName = '/about';

  const AboutScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('About'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            Text(
              'Tourist destinations are opening up again, people are getting vaccinated and life is returning to normal. EuropaOpen helps travelers in the European Region navigate various restrictions and prepare themselves for the bureaucracy ahead.',
              style: Typography.englishLike2018.bodyText1,
            ),
            const SizedBox(height: 12.0),
            const Divider(),
            ListTile(
              contentPadding: EdgeInsets.zero,
              title: const Text('Feedback'),
              subtitle: const Text(
                'We would love to hear from you directly!',
              ),
              onTap: () async => await _sendEmail(),
            ),
            ListTile(
              contentPadding: EdgeInsets.zero,
              title: const Text('Open-source'),
              subtitle: const Text('View project source on GitHub'),
              onTap: () async => await _openGitHubProject(),
            ),
            ListTile(
              contentPadding: EdgeInsets.zero,
              title: const Text(
                'Sponsor the project',
                style: TextStyle(
                  color: ColorTheme.colorProduct,
                ),
              ),
              onTap: () async => await _payPayMeLink(),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _sendEmail() async {
    final uri = Uri(
      scheme: 'mailto',
      path: 'peteralex.developer@gmail.com',
      query: 'subject=${Uri.encodeComponent("Feedback on EuropaOpen")}',
    );
    if (await canLaunch(uri.toString())) {
      await launch(uri.toString());
    }
  }

  Future<void> _openGitHubProject() async {
    const uri = 'https://github.com/sunderee/EuropaOpen';
    if (await canLaunch(uri.toString())) {
      await launch(uri.toString());
    }
  }

  Future<void> _payPayMeLink() async {
    const uri = 'https://paypal.me/sundereecodes';
    if (await canLaunch(uri.toString())) {
      await launch(uri.toString());
    }
  }
}
