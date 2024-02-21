package com.m4ykey.stos.ui.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.databinding.FragmentQuestionsWeekBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsWeekFragment : BaseFragment<FragmentQuestionsWeekBinding>(),
    UIConfigurator<FragmentQuestionsWeekBinding> {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuestionsWeekBinding {
        return FragmentQuestionsWeekBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentQuestionsWeekBinding) {
        binding.apply {  }
    }
}