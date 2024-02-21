package com.m4ykey.stos.ui.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.databinding.FragmentQuestionsFeaturedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFeaturedFragment : BaseFragment<FragmentQuestionsFeaturedBinding>(),
    UIConfigurator<FragmentQuestionsFeaturedBinding> {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuestionsFeaturedBinding {
        return FragmentQuestionsFeaturedBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentQuestionsFeaturedBinding) {
        binding.apply {  }
    }
}