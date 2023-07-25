package com.example.appsessions.Items

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.appsessions.ProfileActivity
import com.example.appsessions.R
import com.example.appsessions.RegulationActivity
import com.example.appsessions.SessionsActivity
import com.example.appsessions.SummaryActivity
import com.google.android.material.navigation.NavigationView

object NavigationMenu {
    fun setupNavigationMenu(
        activity: AppCompatActivity,
        drawerLayout: DrawerLayout,
        navigationView: NavigationView
    ) {
        val colorCustomText = ContextCompat.getColor(activity, R.color.white)
        navigationView.itemTextColor = ColorStateList.valueOf(colorCustomText)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_summary -> {
                    val intent = Intent(activity, SummaryActivity::class.java)
                    activity.startActivity(intent)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_regulation -> {
                    val intent = Intent(activity, RegulationActivity::class.java)
                    activity.startActivity(intent)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_syllabus -> {
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_sessions -> {
                    val intent = Intent(activity, SessionsActivity::class.java)
                    activity.startActivity(intent)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    val intent = Intent(activity, ProfileActivity::class.java)
                    activity.startActivity(intent)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_signOff -> {
                    SessionManager.signOut(activity)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener true
                }
            }
            false
        }

    }

}
