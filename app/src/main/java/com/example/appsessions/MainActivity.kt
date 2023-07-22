package com.example.appsessions
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import com.example.appsessions.Back.ApiClient
import com.example.appsessions.Back.LoginRequest
import com.example.appsessions.Back.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity: AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etUsername.text.toString()
            val password = etPassword.text.toString()
            checkCredentials(email, password)
            //validateCredentialsFireBD(email,password)
        }
    }





    private fun validateCredentials(email: String, password: String, callback: (Boolean) -> Unit) {
        val apiService = ApiClient.getClient()
        val loginRequest = LoginRequest(email, password)

        val call = apiService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val responseBody = response.raw().toString()
                    Toast.makeText(applicationContext, "Inicio de sesión correctamente", Toast.LENGTH_SHORT).show()
                    Log.d("API Response", responseBody)
                    callback(true)
                } else {
                    Toast.makeText(applicationContext, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        })
    }


    private fun checkCredentials(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            validateCredentials(username, password) { isValid ->
                if (isValid) {
                    redirectToNextScreen()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
        }
    }


    private fun redirectToNextScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
