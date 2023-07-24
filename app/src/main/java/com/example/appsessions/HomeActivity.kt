package com.example.appsessions
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle
import android.content.Intent
import android.content.res.ColorStateList
import android.widget.TextView
import androidx.core.content.ContextCompat

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)

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
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val welcomeMessage = "Welcome $name"

        val textViewWelcome: TextView = findViewById(R.id.textViewWelcome)
        textViewWelcome.text = welcomeMessage

        NavigationMenu.setupNavigationMenu(this, drawerLayout, navigationView)
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
