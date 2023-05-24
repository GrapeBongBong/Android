package com.example.android_bong.view.main.community.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityDetailBinding
import com.example.android_bong.view.main.MainActivity

class CommunityDetailActivity : ViewBindingActivity<ActivityCommunityDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityDetailBinding
        get() = ActivityCommunityDetailBinding::inflate


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}