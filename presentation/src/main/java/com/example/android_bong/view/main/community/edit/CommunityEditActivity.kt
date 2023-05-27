package com.example.android_bong.view.main.community.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bong.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_edit)
    }
}