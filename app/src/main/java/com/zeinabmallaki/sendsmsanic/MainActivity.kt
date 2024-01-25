package com.zeinabmallaki.sendsmsanic

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zeinabmallaki.sendsmsanic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener {
            val phoneNumber = binding.phoneNumber.text.toString()
            val message = binding.message.text.toString()

            if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
                sendSMS(phoneNumber, message)
            }
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val smsUri = Uri.parse("sms:$phoneNumber")
        val intent = Intent(Intent.ACTION_SENDTO, smsUri)
        intent.putExtra("sms_body", message)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            toast("Error: SMS app not found")
        }
    }


    private fun toast(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}