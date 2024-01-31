package com.zeinabmallaki.sendsmsanic

import android.telephony.SmsManager

class SMSManager {
    fun sendSMS(mobileNumber: String, message: String): Boolean {
        return try {
            SmsManager.getDefault().sendTextMessage(mobileNumber,
                null, message,
                null,
                null)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}