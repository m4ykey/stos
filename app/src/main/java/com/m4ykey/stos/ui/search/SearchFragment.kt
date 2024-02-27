package com.m4ykey.stos.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.m4ykey.stos.databinding.FragmentSearchBinding
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.UIConfigurator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), UIConfigurator<FragmentSearchBinding> {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        TODO("Not yet implemented")
    }

    override fun setupUI(binding: FragmentSearchBinding) {
        TODO("Not yet implemented")
    }
}