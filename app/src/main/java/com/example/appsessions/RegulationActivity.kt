package com.example.appsessions

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RegulationActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regulation)

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

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_rules)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val rulesList = mutableListOf<Rule>()
        rulesList.add(Rule("1.", "Conéctate directamente desde la plataforma", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("2.", "Comunica a los docentes si tienes complicaciones de audio o video", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("3.", "Entra con video y con micrófono apagado", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("4.", "Utiliza el chat para la sesión de preguntas y respuestas ", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("5.", "Empiezay termina la reunión en el tiempo establecido ", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("6.", "Deja siempre tu camara encendida para validar tu precencia", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("7.", "Hay pase de lista", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("8.", "Las tareas se suben a la plataforma directamente", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("9.", "El material de las presentaciones está en la plataforma ", R.drawable.imgpw, R.drawable.imgpw))
        rulesList.add(Rule("10.", "Solo tienes derecho a una falta para emitir tu certificado", R.drawable.imgpw, R.drawable.imgpw))

        val adapter = RulesAdapter(rulesList)
        recyclerView.adapter = adapter

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
