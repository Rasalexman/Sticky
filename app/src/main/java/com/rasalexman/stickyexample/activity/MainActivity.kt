package com.rasalexman.stickyexample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.rasalexman.kodi.core.*
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.navigation.Navigators.MAIN_NAVIGATOR
import com.rasalexman.stickyexample.presentation.base.INavigationHandler

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainHostFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.mainHostFragment)

    val currentNavFragment: Fragment?
        get() = mainHostFragment?.childFragmentManager?.primaryNavigationFragment

    private val currentNavHandler: INavigationHandler?
        get() = currentNavFragment as? INavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kodi {
            unbind<NavController>(scope = MAIN_NAVIGATOR)
            bind<NavController>() at MAIN_NAVIGATOR with provider {
                Navigation.findNavController(this@MainActivity, R.id.mainHostFragment)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (currentNavHandler?.onSupportNavigateUp() == false && mainHostFragment?.findNavController()?.navigateUp() == true)
    }

    override fun onBackPressed() {
        if (currentNavHandler?.onBackPressed() == false) {
            super.onBackPressed()
        }
    }
}
