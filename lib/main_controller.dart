import 'package:flutter/material.dart';

class MainController extends ChangeNotifier {
  TimeOfDay? selectedTime;

  pickTime(BuildContext context) async {
    final TimeOfDay? picked = await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
      initialEntryMode: TimePickerEntryMode.dial,
    );
    if(picked != null && picked != selectedTime) {
      selectedTime = picked;
      notifyListeners();
    }
  }
}
