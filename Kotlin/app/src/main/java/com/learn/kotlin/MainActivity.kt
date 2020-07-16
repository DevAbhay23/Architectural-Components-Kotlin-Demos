package com.learn.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = this.findNavController(R.id.myHomeHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    // -- The Up button appears in the app bar on every screen except the home screen.
    //   No matter where you are in the app, tapping the Up button takes you to the home screen.
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myHomeHostFragment)
        return navController.navigateUp()
    }
}