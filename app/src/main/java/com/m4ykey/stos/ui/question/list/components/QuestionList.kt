package com.m4ykey.stos.ui.question.list.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.m4ykey.stos.domain.models.questions.Question

@Composable
fun QuestionList(
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    onQuestionClick: (Question) -> Unit,
    questions: LazyPagingItems<Question>
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState
    ) {
        items(
            count = questions.itemCount,
            key = { index ->
                questions[index]?.questionId ?: index
            }
        ) { index ->
            val item = questions[index]
            Log.i("QuestionList", "Processing item at index $index: ${item?.questionId ?: "null"}")
            if (item != null) {
                QuestionListItem(
                    question = item,
                    onClick = {
                        onQuestionClick(item)
                    }
                )
            } else {
                Log.i("QuestionList", "Item at index $index is null")
            }
        }
    }
}
