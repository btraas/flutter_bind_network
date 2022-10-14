import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_bind_network_method_channel.dart';

abstract class FlutterBindNetworkPlatform extends PlatformInterface {
  /// Constructs a FlutterBindNetworkPlatform.
  FlutterBindNetworkPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterBindNetworkPlatform _instance = MethodChannelFlutterBindNetwork();

  /// The default instance of [FlutterBindNetworkPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterBindNetwork].
  static FlutterBindNetworkPlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterBindNetworkPlatform] when
  /// they register themselves.
  static set instance(FlutterBindNetworkPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
