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
import com.m4ykey.stos.common.Constants.ACTIVITY
import com.m4ykey.stos.common.Constants.CREATION
import com.m4ykey.stos.common.Constants.HOT
import com.m4ykey.stos.common.Constants.MONTH
import com.m4ykey.stos.common.Constants.VOTES
import com.m4ykey.stos.common.Constants.WEEK
import com.m4ykey.stos.extensions.ui.BaseFragment
import com.m4ykey.stos.extensions.ui.OnItemClickListener
import com.m4ykey.stos.extensions.ui.UIConfigurator
import com.m4ykey.stos.ui.question.adapter.QuestionAdapter
import com.m4ykey.stos.ui.question.uistate.QuestionUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : BaseFragment<FragmentQuestionBinding>(),
    UIConfigurator<FragmentQuestionBinding>, OnItemClickListener<QuestionItem> {

    private val questionAdapter by lazy { QuestionAdapter(this, requireContext()) }
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
                isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
                    swipeRefresh.isRefreshing = isRefreshing
                }
                swipeRefresh.setOnRefreshListener {
                    viewModel.getQuestions(viewModel.currentSort.value!!)
                }
                questions.observe(viewLifecycleOwner) { state ->
                    handleQuestionState(binding, state)
                }
                currentSort.observe(viewLifecycleOwner) { sort ->
                    updateChipState(sort, binding)
                }
            }
            navController = findNavController()
            recyclerViewQuestions.adapter = questionAdapter

            chipActivity.setOnClickListener { onChipClicked(ACTIVITY, binding) }
            chipCreation.setOnClickListener { onChipClicked(CREATION, binding) }
            chipHot.setOnClickListener { onChipClicked(HOT, binding) }
            chipMonth.setOnClickListener { onChipClicked(MONTH, binding) }
            chipVotes.setOnClickListener { onChipClicked(VOTES, binding) }
            chipWeek.setOnClickListener { onChipClicked(WEEK, binding) }

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
            state?.questionList?.let { questions ->
                progressBar.isVisible = false
                recyclerViewQuestions.isVisible = true
                questionAdapter.submitData(lifecycle, questions)
            }
        }
    }

    private fun onChipClicked(sort : String, binding: FragmentQuestionBinding) {
        viewModel.getQuestions(sort)
        updateChipState(sort, binding)
        viewModel.setCurrentSort(sort)
    }

    private fun updateChipState(sort : String, binding : FragmentQuestionBinding) {
        with(binding) {
            chipWeek.isChecked = sort == WEEK
            chipActivity.isChecked = sort == ACTIVITY
            chipCreation.isChecked = sort == CREATION
            chipHot.isChecked = sort == HOT
            chipMonth.isChecked = sort == MONTH
            chipVotes.isChecked = sort == VOTES
        }
    }
}