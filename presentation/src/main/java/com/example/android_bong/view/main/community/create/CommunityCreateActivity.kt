package com.example.android_bong.view.main.community.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityCommunityCreateBinding

class CommunityCreateActivity : ViewBindingActivity<ActivityCommunityCreateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCommunityCreateBinding
        get() = ActivityCommunityCreateBinding::inflate


    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CommunityCreateActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.community_posting)
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