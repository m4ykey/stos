package com.m4ykey.stos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.m4ykey.stos.databinding.FragmentHomeBinding
import com.m4ykey.stos.ui.adapter.ViewPagerAdapter

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = List(10) { TestFragment() }
        val testAdapter = ViewPagerAdapter(lifecycle = lifecycle, fragmentList = fragmentList, fm = childFragmentManager)
        binding.viewPager2.adapter = testAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = "Tab $position"
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}