package com.alexiacdura.mn_ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.alexiacdura.mn_ui.core.utils.resources.AppConstants
import com.alexiacdura.mn_ui.ui.feed.FeedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_musicnerds.*
import org.koin.android.ext.android.inject


class MusicnerdsActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null

    private val router: MusicnerdsRouter by inject()
    private lateinit var navController: NavController
    private var userId: Int = 0

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_feed -> {
                actionBar!!.title = getString(R.string.title_feed)
                router.openFeedFragment(userId)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                actionBar!!.title = getString(R.string.title_favourites)
                router.openStarredFragment(userId)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                actionBar!!.title = getString(R.string.title_profile)
                router.openProfileFragment(userId)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musicnerds)

        userId = intent.getIntExtra(AppConstants.INTENT_USER, 0)

        setupToolbar()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, FeedFragment.newInstance(userId))
                .commitNow()
        }

        navController = findNavController(this, R.id.navHostFragment)
        router.navController = navController

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.title = getString(R.string.title_feed)
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.setLogo(R.mipmap.ic_mn_logo_icon_toolbar)
        actionBar!!.setDisplayUseLogoEnabled(true)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()

        val backPressedHandled = triggerFragmentBackPressed(currentFragment)

        if (!backPressedHandled) {
            super.onBackPressed()
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.navHostFragment)
    }

    private fun triggerFragmentBackPressed(fragment: Fragment?): Boolean {
        return fragment is FeedFragment
    }

    companion object {
        //Fix userId argument once we have login. Should be user object not int
        fun newIntent(context: Context, userId: Int): Intent {
            val intent = Intent(context, MusicnerdsActivity::class.java)
            intent.putExtra(AppConstants.INTENT_USER, userId)
            return intent
        }
    }
}