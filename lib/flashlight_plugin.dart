import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class FlashlightPlugin {
  static const MethodChannel _channel =
      MethodChannel('com.example.flashlight_plugin');

  static Future<void> turnOn(BuildContext context) =>
      _invoke(context, 'turnOn');

  static Future<void> turnOff(BuildContext context) =>
      _invoke(context, 'turnOff');

  static Future<void> toggle(BuildContext context) =>
      _invoke(context, 'toggle');

  static Future<void> _invoke(
    BuildContext context,
    String method,
  ) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      await showDialog<void>(
        context: context,
        builder: (_) => AlertDialog(
          title: const Text('Not supported'),
          content: const Text(
            'Flashlight control is only available on Android.',
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('OK'),
            ),
          ],
        ),
      );
      return;
    }
    await _channel.invokeMethod<void>(method);
  }
}
