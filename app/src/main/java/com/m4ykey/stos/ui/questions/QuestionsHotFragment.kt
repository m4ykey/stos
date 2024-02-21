package com.m4ykey.stos.ui.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.databinding.FragmentQuestionsHotBinding
import com.m4ykey.stos.extensions.OnItemClickListener
import com.m4ykey.stos.ui.adapter.QuestionPagingAdapter
import com.m4ykey.stos.ui.questions.uistate.QuestionsUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionsHotFragment : BaseFragment<FragmentQuestionsHotBinding>(),
    UIConfigurator<FragmentQuestionsHotBinding>, OnItemClickListener<QuestionItem> {

    private val viewModel : QuestionViewModel by viewModels()
    private val questionAdapter by lazy { QuestionPagingAdapter(this) }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuestionsHotBinding {
        return FragmentQuestionsHotBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentQuestionsHotBinding) {
        lifecycleScope.launch {
            viewModel.getQuestions("activity")
            viewModel.questionsUiState.observe(viewLifecycleOwner) { state ->
                handleUiState(binding, state)
            }
        }
        binding.apply {
            recyclerViewQuestions.adapter = questionAdapter
        }
    }

    private fun handleUiState(binding: FragmentQuestionsHotBinding, state : QuestionsUiState?) {
        binding.apply {
            recyclerViewQuestions.isVisible = state?.isLoading == false && state.questionList != null
            progressBar.isVisible = state?.isLoading == true

            state?.let { nonNullState ->
                nonNullState.error?.let {
                    progressBar.isVisible = false
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                nonNullState.questionList?.let { question ->
                    progressBar.isVisible = false
                    recyclerViewQuestions.isVisible = true
                    questionAdapter.submitData(viewLifecycleOwner.lifecycle, question)
                }
            }
        }
    }

    override fun onItemClick(position: Int, item: QuestionItem) {}
}