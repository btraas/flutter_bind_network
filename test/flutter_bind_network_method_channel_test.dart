import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_bind_network/flutter_bind_network_method_channel.dart';

void main() {
  MethodChannelFlutterBindNetwork platform = MethodChannelFlutterBindNetwork();
  const MethodChannel channel = MethodChannel('flutter_bind_network');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
