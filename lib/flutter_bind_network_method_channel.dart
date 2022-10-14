import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_bind_network_platform_interface.dart';

const NAMESPACE = 'plugins.agartee.com/network_selector';
typedef Future FutureCallback();

/// An implementation of [FlutterBindNetworkPlatform] that uses method channels.
class MethodChannelFlutterBindNetwork extends FlutterBindNetworkPlatform {


  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('$NAMESPACE/methods');


  void bindToBluetoothNetworkTemporarily(FutureCallback callback) async {
    await methodChannel.invokeMethod('bindToBluetoothNetwork');
    await callback();
    await methodChannel.invokeMapMethod('unBindFromNetwork');
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
