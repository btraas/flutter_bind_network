package com.agartee.plugins.flutter_bind_network


import android.content.Context
import android.net.NetworkCapabilities
import android.util.Log
import com.agartee.plugins.NetworkBinder
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
/** FlutterBindNetworkPlugin */
class FlutterBindNetworkPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var applicationContext: Context

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_bind_network")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "bindToBluetoothNetwork") {
        NetworkBinder(NetworkCapabilities.TRANSPORT_BLUETOOTH, "Bluetooth").bind(applicationContext) {
            result.success("bind_success");
        }
    } else if (call.method == "unbind") {
        NetworkBinder(NetworkCapabilities.TRANSPORT_BLUETOOTH, "Bluetooth").unbind(applicationContext);
        result.success("unbind_finished");
    } else if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
      applicationContext = binding.getActivity().getApplicationContext()
  }

  override fun onDetachedFromActivity() {

  }

  override fun onDetachedFromActivityForConfigChanges() {

  }
  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

  }
}
