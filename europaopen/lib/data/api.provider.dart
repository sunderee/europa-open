import 'dart:convert';
import 'dart:io';

import 'package:europaopen/utils/extensions/http_headers.ext.dart';

class ApiProvider {
  final HttpClient _httpClient = HttpClient();
  final String _baseURL;

  ApiProvider(String baseURL) : _baseURL = baseURL;

  Future<String> makeGetRequest(
    String endpoint, {
    Map<String, String>? queryParameters,
    Map<String, String>? headers,
  }) async {
    final url = Uri.https(_baseURL, endpoint, queryParameters);
    final request = await _httpClient.getUrl(url)
      ..headers.addAll(headers);
    final response = await request.close();

    if (response.statusCode < 200 || response.statusCode >= 300) {
      throw ApiException(
        response.statusCode,
        response.reasonPhrase,
      );
    }

    return response
        .transform(const Utf8Decoder(allowMalformed: true))
        .reduce((String previous, String element) => previous + element);
  }
}

class ApiException implements Exception {
  final int statusCode;
  final String cause;

  const ApiException(this.statusCode, this.cause);

  @override
  String toString() => '$statusCode: $cause';
}
