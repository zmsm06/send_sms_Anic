package com.zeinabmallaki.sendsmsanic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeinabmallaki.sendsmsanic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionManager: PermissionManager
    private lateinit var toastManager: ToastManager
    private lateinit var smsManager: SMSManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        permissionManager = PermissionManager(this)
        toastManager = ToastManager(this)
        smsManager = SMSManager()


        binding.sendButton.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val mobileNumber = binding.phoneNumber.text.toString()
        val message = binding.message.text.toString()

        if (permissionManager.hasSendSmsPermission()) {
            val isSent = smsManager.sendSMS(mobileNumber, message)
            if (isSent) {
                toastManager.showToast("SMS sent successfully.")
            } else {
                toastManager.showToast("Failed to send SMS.")
            }
        } else {
            permissionManager.requestSendSmsPermission(PERMISSION_REQUEST_SEND_SMS)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                val mobileNumber = binding.phoneNumber.text.toString()
                val message = binding.message.text.toString()
                val isSent = smsManager.sendSMS(mobileNumber, message)
                if (isSent) {
                    toastManager.showToast("SMS sent successfully.")
                } else {
                    toastManager.showToast("Failed to send SMS.")
                }
            } else {
                toastManager.showToast("Permission denied to send SMS.")
            }
        }
    }


    companion object {
        private const val PERMISSION_REQUEST_SEND_SMS = 123
    }
}