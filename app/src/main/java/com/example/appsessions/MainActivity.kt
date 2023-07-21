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
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etUsername.text.toString()
            val password = etPassword.text.toString()
            //checkCredentials(email, password)
            validateCredentialsFireBD(email,password)
        }
    }


    private fun validateCredentialsFireBD(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                    setSeason("5")
                    getSeasons()

                } else {
                    Toast.makeText(this, "Error en el inicio de sesión: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun getSeasons() {
        val reference = database.getReference("seasons")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (seasonSnapshot in dataSnapshot.children) {
                    val season = seasonSnapshot.getValue(Season::class.java)
                    val message = "{ \"name\" : \"${season?.name}\", \"description\" : \"${season?.description}\", \"status\" : ${season?.status} }"

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    Log.d("Season Data", message)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error al leer los datos ${databaseError.message}")
            }
        })
    }

    fun setSeason(id:String) {
        val reference = database.getReference("seasons")

        val season = Season("Seasión prueba2","Descripción prueba",false)

        reference.child(id).setValue(season).addOnCompleteListener {
            Toast.makeText(this@MainActivity, "Se guardo la info", Toast.LENGTH_LONG).show()
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
