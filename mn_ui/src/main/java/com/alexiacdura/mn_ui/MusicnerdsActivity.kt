package com.alexiacdura.mn_ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_musicnerds.*


class MusicnerdsActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private var actionBar: ActionBar? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_feed -> {
                textMessage.setText(R.string.title_feed)
                actionBar!!.title = getString(R.string.title_feed)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                textMessage.setText(R.string.title_favourites)
                actionBar!!.title = getString(R.string.title_favourites)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                textMessage.setText(R.string.title_profile)
                actionBar!!.title = getString(R.string.title_profile)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musicnerds)

        setupToolbar()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.title = getString(R.string.title_feed)
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.setLogo(R.mipmap.ic_mn_logo_icon_toolbar)
        actionBar!!.setDisplayUseLogoEnabled(true)
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