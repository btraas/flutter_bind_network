
import 'flutter_bind_network_platform_interface.dart';


class FlutterBindNetwork {
  Future<String?> getPlatformVersion() {
    return FlutterBindNetworkPlatform.instance.getPlatformVersion();
  }
}

