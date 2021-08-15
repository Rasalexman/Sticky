package com.rasalexman.stickyexample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.rasalexman.kodi.core.*
import com.rasalexman.stickyexample.R
import com.rasalexman.stickyexample.navigation.Navigators.MAIN_NAVIGATOR
import com.rasalexman.stickyexample.presentation.base.INavigationHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val currentNavHandler: INavigationHandler?
        get() = mainHostFragment.childFragmentManager.primaryNavigationFragment as? INavigationHandler

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
        return (currentNavHandler?.onSupportNavigateUp() == false && mainHostFragment.findNavController().navigateUp())
    }

    override fun onBackPressed() {
        if (currentNavHandler?.onBackPressed() == false) {
            super.onBackPressed()
        }
    }
}
