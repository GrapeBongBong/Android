package com.example.android_bong.view.main.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentCommunityBinding

class CommunityFragment : ViewBindingFragment<FragmentCommunityBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCommunityBinding
        get() = FragmentCommunityBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}