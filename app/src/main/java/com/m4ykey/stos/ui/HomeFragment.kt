package com.m4ykey.stos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.FragmentHomeBinding
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.OnItemClickListener
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.ui.adapter.QuestionAdapter
import com.m4ykey.stos.ui.uistate.QuestionUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), UIConfigurator<FragmentHomeBinding>, OnItemClickListener<QuestionItem> {

    private val questionAdapter by lazy { QuestionAdapter(this) }
    private val viewModel : HomeViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentHomeBinding) {
        viewModel.questions.observe(viewLifecycleOwner) { state ->
            handleQuestionState(binding, state)
        }

        binding.recyclerViewQuestions.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClick(position: Int, item: QuestionItem) {}

    private fun handleQuestionState(binding: FragmentHomeBinding, state : QuestionUiState?) {
        with(binding) {
            recyclerViewQuestions.isVisible = state?.isLoading == false && state.questionList != null
            progressBar.isVisible = state?.isLoading == true

            state?.error?.let {
                progressBar.isVisible = false
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            state?.questionList?.let { search ->
                progressBar.isVisible = false
                recyclerViewQuestions.isVisible = true
                questionAdapter.submitData(viewLifecycleOwner.lifecycle, search)
            }
        }
    }

}