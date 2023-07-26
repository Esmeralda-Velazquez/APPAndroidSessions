package com.example.appsessions
import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.TextView
import android.widget.Toast
import com.example.appsessions.Back.ApiClient
import com.example.appsessions.Items.NavigationMenu
import com.example.appsessions.Models.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var crown1: ImageView
    private lateinit var crown2: ImageView
    private lateinit var crown3: ImageView
    private lateinit var crown4: ImageView
    private lateinit var crown5: ImageView
    private lateinit var completedTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        completedTextView = findViewById(R.id.completed)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        toggle.drawerArrowDrawable.color = resources.getColor(android.R.color.white)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val welcomeMessage = "Welcome $name"

        val textViewWelcome: TextView = findViewById(R.id.textViewWelcome)
        textViewWelcome.text = welcomeMessage

        NavigationMenu.setupNavigationMenu(this, drawerLayout, navigationView)
        crown1 = findViewById(R.id.crown1)
        crown2 = findViewById(R.id.crown2)
        crown3 = findViewById(R.id.crown3)
        crown4 = findViewById(R.id.crown4)
        crown5 = findViewById(R.id.crown5)

        getSessionsFromService()
    }
    private fun getSessionsFromService() {
        val apiService = ApiClient.getClient()

        GlobalScope.launch(Dispatchers.IO) {
            val call: retrofit2.Call<List<Session>> = apiService.getAllSessions()
            call.enqueue(object : Callback<List<Session>> {
                override fun onResponse(call: retrofit2.Call<List<Session>>, response: Response<List<Session>>) {
                    if (response.isSuccessful) {
                        val sessionList = response.body()
                        if (!sessionList.isNullOrEmpty()) {
                            updateCrowns(sessionList)
                        } else {

                        }
                    } else {
                        Toast.makeText(applicationContext, "Respuesta no exitosa", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<Session>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fallo la solicitud", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun updateCrowns(sessionList: List<Session>) {

        val completedSessionsCount = sessionList.count { it.status == "true" }

        when (completedSessionsCount) {
            0 -> setGrayCrowns()
            1 -> setOneYellowCrown()
            3 -> setThreeYellowCrown()
        }
        completedTextView.text = completedSessionsCount.toString()
    }
    private fun setGrayCrowns() {
        crown1.setImageResource(R.drawable.crown_gray)
        crown2.setImageResource(R.drawable.crown_gray)
        crown3.setImageResource(R.drawable.crown_gray)
        crown4.setImageResource(R.drawable.crown_gray)
        crown5.setImageResource(R.drawable.crown_gray)
    }

    private fun setOneYellowCrown() {
        crown1.setImageResource(R.drawable.crown_yellow)
        crown2.setImageResource(R.drawable.crown_gray)
        crown3.setImageResource(R.drawable.crown_gray)
        crown4.setImageResource(R.drawable.crown_gray)
        crown5.setImageResource(R.drawable.crown_gray)
    }
    private fun setThreeYellowCrown() {
        crown1.setImageResource(R.drawable.crown_yellow)
        crown2.setImageResource(R.drawable.crown_yellow)
        crown3.setImageResource(R.drawable.crown_yellow)
        crown4.setImageResource(R.drawable.crown_gray)
        crown5.setImageResource(R.drawable.crown_gray)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView)
            } else {
                drawerLayout.openDrawer(navigationView)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
