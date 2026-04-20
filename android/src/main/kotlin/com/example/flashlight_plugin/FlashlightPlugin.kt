package com.example.flashlight_plugin

import android.content.Context
import android.hardware.camera2.CameraManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlashlightPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var isOn = false

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(
            binding.binaryMessenger,
            "com.example.flashlight_plugin",
        )
        channel.setMethodCallHandler(this)
        cameraManager = binding.applicationContext
            .getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList.firstOrNull()
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "turnOn" -> { setTorch(true); result.success(null) }
            "turnOff" -> { setTorch(false); result.success(null) }
            "toggle" -> { setTorch(!isOn); result.success(null) }
            else -> result.notImplemented()
        }
    }

    private fun setTorch(on: Boolean) {
        cameraId?.let {
            cameraManager.setTorchMode(it, on)
            isOn = on
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
