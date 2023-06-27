package com.example.appsessions
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent


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
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Aquí puedes agregar la lógica para validar las credenciales de inicio de sesión
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                if (validateCredentials(username, password)) {
                    // Credenciales válidas, redirige a la siguiente pantalla
                    redirectToNextScreen()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateCredentials(username: String, password: String): Boolean {
        // Aquí puedes implementar la lógica de validación de las credenciales
        // Por ejemplo, puedes hacer una consulta a una base de datos o comparar con datos predefinidos
        return username == "admin" && password == "password"
    }

    private fun redirectToNextScreen() {
        // Aquí defines la actividad de destino a la que quieres redirigir después del inicio de sesión exitoso
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
