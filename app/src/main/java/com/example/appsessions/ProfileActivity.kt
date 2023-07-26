package com.example.appsessions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appsessions.Back.ApiService
import com.example.appsessions.Items.NavigationMenu
import com.example.appsessions.Models.ProfileUpdateData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ProfileActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.white)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        NavigationMenu.setupNavigationMenu(this, drawerLayout, navigationView)

        // Crear la instancia de Retrofit y ApiService
        val retrofit = Retrofit.Builder()
            .baseUrl("http://tu_dominio_del_backend.com") // Reemplaza con la URL de tu backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val studies = sharedPreferences.getString("studies", "")
        val token = sharedPreferences.getString("token", "") ?: ""


        val usernameTextView = findViewById<TextView>(R.id.usernameProfile)
        val emailTextView = findViewById<TextView>(R.id.emailProfile)
        val studiesTextView = findViewById<TextView>(R.id.studiesProfile)

        usernameTextView.text = name
        emailTextView.text = email
        studiesTextView.text = studies

        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        btnActualizar.setOnClickListener {
            val newName = findViewById<EditText>(R.id.editName).text.toString()
            val newEmail = findViewById<EditText>(R.id.editEmail).text.toString()
            val newStudies = findViewById<EditText>(R.id.editStudies).text.toString()
            val newPassword = findViewById<EditText>(R.id.editPassword).text.toString()


            val profileUpdateData = ProfileUpdateData(newName, newEmail, newStudies, newPassword, token)

            val call = apiService.updateProfile(profileUpdateData)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
                        usernameTextView.text = newName
                        emailTextView.text = newEmail
                        studiesTextView.text = newStudies
                    } else {
                        // Hubo un error en la solicitud, muestra un mensaje de error al usuario
                        Toast.makeText(this@ProfileActivity, "Error de solicitud", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    if (t is IOException) {
                        // Error de red, muestra un mensaje indicando un problema con la conexión a internet
                        Toast.makeText(this@ProfileActivity, "Error de red. Por favor, verifica tu conexión a internet.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ProfileActivity, "Ha ocurrido un error. Por favor, inténtalo de nuevo más tarde.", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
