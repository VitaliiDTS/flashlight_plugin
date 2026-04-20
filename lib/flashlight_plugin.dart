
import 'flashlight_plugin_platform_interface.dart';

class FlashlightPlugin {
  Future<String?> getPlatformVersion() {
    return FlashlightPluginPlatform.instance.getPlatformVersion();
  }
}
