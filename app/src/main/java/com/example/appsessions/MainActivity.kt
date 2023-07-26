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
import org.json.JSONObject


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
        }

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val token = sharedPreferences.getString("token", "")

        if (name?.isNotEmpty() == true && token?.isNotEmpty() == true) {
            redirectToNextScreen()
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
                    if (loginResponse != null) {
                        val name = loginResponse.name
                        val token = loginResponse.token
                        val email = loginResponse.email
                        val studies = loginResponse.studies
                        if (name != null && token != null && name.isNotEmpty() && token.isNotEmpty()) {
                            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("name", name)
                            editor.putString("token", token)
                            editor.putString("email", email)
                            editor.putString("studies",studies )
                            editor.apply()
                            Toast.makeText(applicationContext, "Credenciales correctas", Toast.LENGTH_SHORT).show()
                            Log.d("API Response", "JSON Response: $loginResponse")
                            callback(true)
                        } else {
                            Log.d("API Response", "El name o token están vacíos o son nulos")
                            callback(false)
                        }
                    } else {
                        Log.d("API Response", "loginResponse es nulo")
                        callback(false)
                    }
                } else {
                    Toast.makeText(applicationContext, "Llena los campos", Toast.LENGTH_SHORT).show()
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
        finish()
    }
}
