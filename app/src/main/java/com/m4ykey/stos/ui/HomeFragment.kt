package com.m4ykey.stos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.R
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.databinding.FragmentHomeBinding
import com.m4ykey.stos.ui.adapter.ViewPagerAdapter
import com.m4ykey.stos.ui.questions.QuestionsFeaturedFragment
import com.m4ykey.stos.ui.questions.QuestionsHotFragment
import com.m4ykey.stos.ui.questions.QuestionsUnansweredFragment
import com.m4ykey.stos.ui.questions.QuestionsWeekFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), UIConfigurator<FragmentHomeBinding> {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentHomeBinding) {
        with(binding) {
            val fragmentList = listOf(
                QuestionsHotFragment(),
                QuestionsWeekFragment(),
                QuestionsFeaturedFragment(),
                QuestionsUnansweredFragment()
            )
            val testAdapter = ViewPagerAdapter(
                lifecycle = lifecycle,
                fragmentList = fragmentList,
                fm = childFragmentManager)

            viewPager2.adapter = testAdapter
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                val fragment = fragmentList[position]
                tab.text = when (fragment) {
                    is QuestionsHotFragment -> requireContext().getString(R.string.hot)
                    is QuestionsWeekFragment -> requireContext().getString(R.string.weeks_top)
                    is QuestionsFeaturedFragment -> requireContext().getString(R.string.featured)
                    is QuestionsUnansweredFragment -> requireContext().getString(R.string.unanswered)
                    else -> ""
                }
            }.attach()
        }
    }
}