package com.example.appsessions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import android.widget.ImageView


class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_item1 -> {
                    // Acción para el elemento del menú 1
                    Toast.makeText(this, "Opción 1 seleccionada", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_item2 -> {
                    // Acción para el elemento del menú 2
                    Toast.makeText(this, "Opción 2 seleccionada", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_item3 -> {
                    // Acción para el elemento del menú 3
                    Toast.makeText(this, "Opción 3 seleccionada", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
        cambiarCoronas(0)
    }
    fun cambiarCoronas(estadoCurso: Int) {
        val crown1 = findViewById<ImageView>(R.id.crown1)
        val crown2 = findViewById<ImageView>(R.id.crown2)
        val crown3 = findViewById<ImageView>(R.id.crown3)
        val crown4 = findViewById<ImageView>(R.id.crown4)
        val crown5 = findViewById<ImageView>(R.id.crown5)

        if (estadoCurso == 0) {
            crown1.setImageResource(R.drawable.crown_gray)
            crown2.setImageResource(R.drawable.crown_gray)
            crown3.setImageResource(R.drawable.crown_gray)
            crown4.setImageResource(R.drawable.crown_gray)
            crown5.setImageResource(R.drawable.crown_gray)
        } else if (estadoCurso == 1) {
            crown1.setImageResource(R.drawable.crown_yellow)
            crown2.setImageResource(R.drawable.crown_yellow)
            crown3.setImageResource(R.drawable.crown_yellow)
            crown4.setImageResource(R.drawable.crown_yellow)
            crown5.setImageResource(R.drawable.crown_yellow)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(navigationView)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
