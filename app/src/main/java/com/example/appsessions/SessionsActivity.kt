package com.example.appsessions

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appsessions.Back.ApiClient
import com.example.appsessions.Items.NavigationMenu
import com.example.appsessions.Models.Session
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionsActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)
        val layout = findViewById<LinearLayout>(R.id.button_container)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        NavigationMenu.setupNavigationMenu(this, drawerLayout, navigationView)

        val apiService = ApiClient.getClient()

        GlobalScope.launch(Dispatchers.IO) {
            val call: Call<List<Session>> = apiService.getAllSessions()
            call.enqueue(object : Callback<List<Session>> {
                override fun onResponse(call: Call<List<Session>>, response: Response<List<Session>>) {
                    if (response.isSuccessful) {
                        val sessionList = response.body()
                        if (!sessionList.isNullOrEmpty()) {
                            runOnUiThread {
                                val layout = findViewById<LinearLayout>(R.id.button_container)

                                for ((index, session) in sessionList.withIndex()) {
                                    val button = createButton(index + 1,session.title, session.url)
                                    layout.addView(button)
                                }
                            }

                        } else {

                        }
                    } else {
                        Toast.makeText(applicationContext, "Respuesta no exitosa", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Session>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fallo la solicitud", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun createButton(number: Int, title: String, url: String): Button {
        val button = Button(this)
        button.text = "${number}. $title"
        button.setBackgroundColor(resources.getColor(R.color.primary))
        button.setTextColor(resources.getColor(android.R.color.white))
        button.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        val buttonMargin = resources.getDimensionPixelSize(R.dimen.button_margin)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.button_height)
        )
        layoutParams.setMargins(buttonMargin, buttonMargin, buttonMargin, buttonMargin)
        button.layoutParams = layoutParams

        return button
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
}
