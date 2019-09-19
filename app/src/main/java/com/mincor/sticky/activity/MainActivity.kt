package com.mincor.sticky.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mincor.kodi.core.bind
import com.mincor.kodi.core.kodi
import com.mincor.kodi.core.provider
import com.mincor.kodi.core.unbind
import com.mincor.sticky.R
import com.mincor.sticky.navigation.Navigators.MAIN_NAVIGATOR
import com.mincor.sticky.presentation.base.INavigationHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentNavHandler: INavigationHandler?
        get() = mainHostFragment.childFragmentManager.primaryNavigationFragment as? INavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kodi {
            unbind<NavController>(MAIN_NAVIGATOR)
            bind<NavController>(MAIN_NAVIGATOR) with provider {
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
