package com.example.android_bong.view.main.talentexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_bong.databinding.FragmentTalentExchangeBinding

class TalentExchangeFragment : Fragment() {

    private var _binding: FragmentTalentExchangeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val talentExchangeViewModel =
            ViewModelProvider(this).get(TalentExchangeViewModel::class.java)

        _binding = FragmentTalentExchangeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTalentExchange
        talentExchangeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}