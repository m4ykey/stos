package com.m4ykey.stos.question.presentation.components.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.core.views.CenteredContent
import com.m4ykey.stos.question.presentation.list.QuestionSort
import com.m4ykey.stos.question.presentation.list.QuestionSort.ACTIVITY
import com.m4ykey.stos.question.presentation.list.QuestionSort.CREATION
import com.m4ykey.stos.question.presentation.list.QuestionSort.HOT
import com.m4ykey.stos.question.presentation.list.QuestionSort.MONTH
import com.m4ykey.stos.question.presentation.list.QuestionSort.VOTES
import com.m4ykey.stos.question.presentation.list.QuestionSort.WEEK
import org.jetbrains.compose.resources.stringResource
import stos.composeapp.generated.resources.Res
import stos.composeapp.generated.resources.activity
import stos.composeapp.generated.resources.creation
import stos.composeapp.generated.resources.hot
import stos.composeapp.generated.resources.month
import stos.composeapp.generated.resources.votes
import stos.composeapp.generated.resources.week

@Composable
fun ChipList(
    modifier : Modifier = Modifier,
    onChipSelected : (QuestionSort) -> Unit,
    selectedChip : QuestionSort
) {
    val chipList = QuestionSort.entries.map { sort ->
        sort.getLabel() to sort
    }

    CenteredContent(modifier = modifier) { contentModifier ->
        LazyRow(
            modifier = contentModifier.padding(horizontal = 5.dp)
        ) {
            items(chipList) { (label, sortKey) ->
                ChipItem(
                    title = label,
                    selected = selectedChip == sortKey,
                    onSelect = { onChipSelected(sortKey) },
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun QuestionSort.getLabel() : String {
    return when (this) {
        HOT -> stringResource(Res.string.hot)
        ACTIVITY -> stringResource(Res.string.activity)
        VOTES -> stringResource(Res.string.votes)
        CREATION -> stringResource(Res.string.creation)
        WEEK -> stringResource(Res.string.week)
        MONTH -> stringResource(Res.string.month)
    }
}