package com.m4ykey.stos.ui.question

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.m4ykey.stos.R
import com.m4ykey.stos.data.domain.model.question.QuestionItem
import com.m4ykey.stos.databinding.FragmentQuestionDetailBinding
import com.m4ykey.stos.extensions.convertToMarkdown
import com.m4ykey.stos.extensions.loadImage
import com.m4ykey.stos.extensions.ui.BaseFragment
import com.m4ykey.stos.extensions.ui.UIConfigurator
import com.m4ykey.stos.ui.question.uistate.QuestionDetailUiState
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import io.noties.markwon.image.coil.CoilImagesPlugin
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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

        binding.apply {
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
            lifecycleScope.launch {
                viewModel.apply {
                    getQuestionDetails(args.questionId)
                    detail.observe(viewLifecycleOwner) { state -> handleDetailState(state) }
                }
            }
        }
    }

    private fun FragmentQuestionDetailBinding.handleDetailState(state : QuestionDetailUiState?) {
        progressBar.isVisible = state?.isLoading == true
        linearLayoutBody.isVisible = state?.isLoading == false && state.detail != null

        state?.error?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            progressBar.isVisible = false
        }
        state?.detail?.let { question ->
            progressBar.isVisible = false
            displayQuestionDetail(question)
        }
    }

    private fun FragmentQuestionDetailBinding.displayQuestionDetail(item : QuestionItem) {
        with(item) {
            txtTitle.text = Jsoup.parse(title).text()
            txtAuthor.text = Jsoup.parse(owner.displayName).text()
            imgAuthor.loadImage(owner.profileImage)

            val document : Document = Jsoup.parse(body)
            val markdown = convertToMarkdown(document)

            val markwon = Markwon.builder(requireContext())
                .usePlugin(CoilImagesPlugin.create(requireContext())).build()

            markwon.setMarkdown(txtBody, markdown)

            for (tag in tags) {
                val chip = Chip(requireContext())
                chip.text = tag
                chip.isClickable = true
                chip.setOnClickListener {
                    val action = QuestionDetailFragmentDirections.actionQuestionDetailFragmentToQuestionTagsFragment(tag)
                    navController.navigate(action)
                }
                chipGroup.addView(chip)
            }

            val buttons = listOf(
                Pair(R.id.imgShare) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    val chooser = Intent.createChooser(intent, link)
                    context?.startActivity(chooser)
                },
                Pair(R.id.imgSite) {
                    context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
                }
            )
            for ((itemId, action) in buttons) {
                toolbar.menu.findItem(itemId)?.setOnMenuItemClickListener {
                    action.invoke()
                    true
                }
            }
        }
    }
}