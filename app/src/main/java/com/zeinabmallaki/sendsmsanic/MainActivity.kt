package com.zeinabmallaki.sendsmsanic

import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zeinabmallaki.sendsmsanic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val mobileNumber = binding.phoneNumber.text.toString()
        val message = binding.message.text.toString()

        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendSMS(mobileNumber, message)
        } else {
            requestPermissions(arrayOf(Manifest.permission.SEND_SMS), PERMISSION_REQUEST_SEND_SMS)
        }
    }

    private fun sendSMS(mobileNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(mobileNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent successfully.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val mobileNumber = binding.phoneNumber.text.toString()
                val message = binding.message.text.toString()
                sendSMS(mobileNumber, message)
            } else {
                Toast.makeText(this, "Permission denied to send SMS.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_SEND_SMS = 123
    }
}