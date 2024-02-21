package com.m4ykey.stos.ui.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.databinding.FragmentQuestionsUnansweredBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsUnansweredFragment : BaseFragment<FragmentQuestionsUnansweredBinding>(),
    UIConfigurator<FragmentQuestionsUnansweredBinding> {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuestionsUnansweredBinding {
        return FragmentQuestionsUnansweredBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentQuestionsUnansweredBinding) {
        binding.apply {  }
    }
}