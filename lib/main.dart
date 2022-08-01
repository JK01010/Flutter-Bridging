// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bridge/main_controller.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  //channel name, used in android code to invoke method
  static const channel =
      MethodChannel('com.example.flutter_bridge/native_bridge');

  const MyHomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    MainController mainController = MainController();
    return AnimatedBuilder(
      animation: mainController,
      builder: (BuildContext context, Widget? child) {
        return Scaffold(
          body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text("Call Native Code to show Toast Message"),
                ElevatedButton(
                  onPressed: () {
                    _showToast();
                  },
                  child: const Text("Create Channel"),
                ),
                ElevatedButton(
                  onPressed: () async {
                    await mainController.pickTime(context);
                    if (mainController.selectedTime != null) {
                      _setTime(mainController.selectedTime!.hour,
                          mainController.selectedTime!.minute);
                    }
                  },
                  child: const Text("Set Time"),
                ),
                ElevatedButton(
                  onPressed: () {
                    _setAlarm();
                  },
                  child: const Text("Set Alarm"),
                ),
                const SizedBox(
                  height: 20,
                ),
                if (mainController.selectedTime != null)
                  Text(
                    'Selected Time: ${mainController.selectedTime!.toString()}',
                  ),
              ],
            ),
          ),
        );
      },
    );
  }

  //a method that invoke native code
  Future<void> _showToast() async {
    channel.invokeMethod("showToast");
  }

  Future<void> _setTime(int hour, int minute) async {
    channel.invokeMethod(
      "setTime",
      <String, int>{
        "hour": hour,
        "minute": minute,
      },
    );
  }

  Future<void> _setAlarm() async {
    channel.invokeMethod("setAlarm");
  }
}
