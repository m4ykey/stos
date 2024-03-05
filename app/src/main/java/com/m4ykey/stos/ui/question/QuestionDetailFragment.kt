package com.m4ykey.stos.ui.question

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.FragmentQuestionDetailBinding
import com.m4ykey.stos.extensions.BaseFragment
import com.m4ykey.stos.extensions.UIConfigurator
import com.m4ykey.stos.ui.question.uistate.QuestionDetailUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionDetailFragment :
    BaseFragment<FragmentQuestionDetailBinding>(), UIConfigurator<FragmentQuestionDetailBinding> {

    private lateinit var navController: NavController
    private val viewModel : QuestionViewModel by viewModels()
    private val args : QuestionDetailFragmentArgs by navArgs()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuestionDetailBinding {
        return FragmentQuestionDetailBinding.inflate(inflater, container, false)
    }

    override fun setupUI(binding: FragmentQuestionDetailBinding) {
        navController = findNavController()

        lifecycleScope.launch {
            viewModel.getQuestionDetails(args.questionId)
        }

        viewModel.detail.observe(viewLifecycleOwner) { state -> handleDetailState(state, binding) }
    }

    private fun handleDetailState(state : QuestionDetailUiState?, binding: FragmentQuestionDetailBinding) {
        with(binding) {
            progressBar.isVisible = state?.isLoading == true
            linearLayoutBody.isVisible = state?.isLoading == false && state.detail != null

            state?.error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                progressBar.isVisible = false
            }
            state?.detail?.let { question ->
                progressBar.isVisible = false
                displayQuestionDetail(question, binding)
            }
        }
    }

    private fun displayQuestionDetail(questionDetail : QuestionItem, binding : FragmentQuestionDetailBinding) {
        with(binding) {
            txtAuthor.text = questionDetail.owner.displayName
            txtTitle.text = questionDetail.title

            imgAuthor.load(questionDetail.owner.profileImage) {
                crossfade(true)
                crossfade(500)
            }
        }
    }
}