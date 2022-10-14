import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_bind_network_platform_interface.dart';

const NAMESPACE = 'plugins.agartee.com/network_binder';
typedef Future FutureCallback();

/// An implementation of [FlutterBindNetworkPlatform] that uses method channels.
class FlutterAndroidNetworkBinder extends FlutterNetworkBinderPlatform {

  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('$NAMESPACE/methods');


  Future bindToBluetoothNetwork() async {
    await methodChannel.invokeMethod('bindToBluetoothNetwork');
  }
  Future unbind() async {
    await methodChannel.invokeMethod('unbind');
  }

  Future<bool> ping({required String host, int port = 80, int timeout = 2000}) async {
    var result = await methodChannel.invokeMethod('ping', [host, port, timeout]);
    return result == "true";
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
