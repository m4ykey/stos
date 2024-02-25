package com.m4ykey.stos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
        binding.apply {
            viewModel.apply {
                questions.observe(viewLifecycleOwner) { state ->
                    handleQuestionState(binding, state)
                }

                currentSort.observe(viewLifecycleOwner) { sort ->
                    updateChipState(sort, binding)
                }
            }

            recyclerViewQuestions.adapter = questionAdapter

            chipActivity.setOnClickListener { onChipClicked("activity", binding) }
            chipCreation.setOnClickListener { onChipClicked("creation", binding) }
            chipHot.setOnClickListener { onChipClicked("hot", binding) }
            chipMonth.setOnClickListener { onChipClicked("month", binding) }
            chipVotes.setOnClickListener { onChipClicked("votes", binding) }
            chipWeek.setOnClickListener { onChipClicked("week", binding) }
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
                questionAdapter.submitData(lifecycle, search)
            }
        }
    }

    private fun onChipClicked(sort : String, binding: FragmentHomeBinding) {
        viewModel.getQuestions(sort)
        updateChipState(sort, binding)
    }

    private fun updateChipState(sort : String, binding : FragmentHomeBinding) {
        with(binding) {
            chipWeek.isChecked = sort == "week"
            chipActivity.isChecked = sort == "activity"
            chipCreation.isChecked = sort == "creation"
            chipHot.isChecked = sort == "hot"
            chipMonth.isChecked = sort == "month"
            chipVotes.isChecked = sort == "votes"
        }
    }
}
