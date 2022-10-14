import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_bind_network/flutter_bind_network.dart';
import 'package:flutter_bind_network/flutter_bind_network_platform_interface.dart';
import 'package:flutter_bind_network/flutter_bind_network_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterBindNetworkPlatform 
    with MockPlatformInterfaceMixin
    implements FlutterBindNetworkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterBindNetworkPlatform initialPlatform = FlutterBindNetworkPlatform.instance;

  test('$MethodChannelFlutterBindNetwork is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterBindNetwork>());
  });

  test('getPlatformVersion', () async {
    FlutterBindNetwork flutterBindNetworkPlugin = FlutterBindNetwork();
    MockFlutterBindNetworkPlatform fakePlatform = MockFlutterBindNetworkPlatform();
    FlutterBindNetworkPlatform.instance = fakePlatform;
  
    expect(await flutterBindNetworkPlugin.getPlatformVersion(), '42');
  });
}
