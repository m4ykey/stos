package com.m4ykey.stos.ui.question

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.m4ykey.stos.R
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.FragmentQuestionBinding
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.OnItemClickListener
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.ui.question.adapter.QuestionAdapter
import com.m4ykey.stos.ui.uistate.QuestionUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : BaseFragment<FragmentQuestionBinding>(), UIConfigurator<FragmentQuestionBinding>, OnItemClickListener<QuestionItem> {

    private val questionAdapter by lazy { QuestionAdapter(this) }
    private val viewModel : QuestionViewModel by viewModels()
    private lateinit var navController: NavController

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuestionBinding {
        return FragmentQuestionBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentQuestionBinding) {
        binding.apply {
            viewModel.apply {
                questions.observe(viewLifecycleOwner) { state ->
                    handleQuestionState(binding, state)
                }

                currentSort.observe(viewLifecycleOwner) { sort ->
                    updateChipState(sort, binding)
                }
            }
            navController = findNavController()
            recyclerViewQuestions.adapter = questionAdapter

            chipActivity.setOnClickListener { onChipClicked("activity", binding) }
            chipCreation.setOnClickListener { onChipClicked("creation", binding) }
            chipHot.setOnClickListener { onChipClicked("hot", binding) }
            chipMonth.setOnClickListener { onChipClicked("month", binding) }
            chipVotes.setOnClickListener { onChipClicked("votes", binding) }
            chipWeek.setOnClickListener { onChipClicked("week", binding) }

            toolbar.menu.findItem(R.id.imgSearch).setOnMenuItemClickListener {
                val action = QuestionFragmentDirections.actionQuestionFragmentToSearchFragment()
                navController.navigate(action)
                true
            }
        }
    }

    override fun onItemClick(position: Int, item: QuestionItem) {
        val action = QuestionFragmentDirections.actionQuestionFragmentToQuestionDetailFragment(item.questionId)
        navController.navigate(action)
    }

    private fun handleQuestionState(binding: FragmentQuestionBinding, state : QuestionUiState?) {
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

    private fun onChipClicked(sort : String, binding: FragmentQuestionBinding) {
        viewModel.getQuestions(sort)
        updateChipState(sort, binding)
    }

    private fun updateChipState(sort : String, binding : FragmentQuestionBinding) {
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
