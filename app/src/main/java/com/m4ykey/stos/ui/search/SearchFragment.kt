package com.m4ykey.stos.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.databinding.FragmentSearchBinding
import com.m4ykey.stos.extensions.ui.BaseFragment
import com.m4ykey.stos.extensions.ui.UIConfigurator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(),
    UIConfigurator<FragmentSearchBinding> {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentSearchBinding) {

    }
}