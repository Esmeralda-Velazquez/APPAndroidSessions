package com.example.appsessions.Items

import android.content.Context
import android.content.Intent
import com.example.appsessions.MainActivity


object SessionManager {
    fun signOut(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("name")
        editor.remove("token")
        editor.remove("email")
        editor.remove("studies")
        editor.apply()

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}

