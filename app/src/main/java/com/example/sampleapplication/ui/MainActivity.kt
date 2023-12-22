package com.example.sampleapplication.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.sampleapplication.R
import com.example.sampleapplication.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.nav_graph, intent.extras)
    }
}
