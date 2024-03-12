package com.m4ykey.stos.extensions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment(), UIConfigurator<T> {

    private var _binding : T? = null
    private val binding get() = _binding!!

    abstract fun createBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        setupUI(binding)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}