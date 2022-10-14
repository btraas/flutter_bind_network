import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_bind_network_platform_interface.dart';

const NAMESPACE = 'plugins.agartee.com/network_selector';
typedef Future FutureCallback();

/// An implementation of [FlutterBindNetworkPlatform] that uses method channels.
class FlutterNetworkBinder extends FlutterNetworkBinderPlatform {

  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('$NAMESPACE/methods');


  Future bindToBluetoothNetworkTemporarily(FutureCallback callback) async {
    await methodChannel.invokeMethod('bindToBluetoothNetwork');
    var result = await callback();
    await methodChannel.invokeMapMethod('unBindFromNetwork');
    return result;
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
