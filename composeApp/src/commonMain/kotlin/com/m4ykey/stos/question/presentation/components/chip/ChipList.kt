package com.m4ykey.stos.question.presentation.components.chip

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.m4ykey.stos.question.presentation.QuestionSort
import com.m4ykey.stos.question.presentation.QuestionSort.HOT
import com.m4ykey.stos.question.presentation.QuestionSort.ACTIVITY
import com.m4ykey.stos.question.presentation.QuestionSort.VOTES
import com.m4ykey.stos.question.presentation.QuestionSort.CREATION
import com.m4ykey.stos.question.presentation.QuestionSort.WEEK
import com.m4ykey.stos.question.presentation.QuestionSort.MONTH
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

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        items(chipList) { (label, sortKey) ->
            ChipItem(
                title = label,
                selected = selectedChip == sortKey,
                onSelect = { onChipSelected(sortKey) },
                modifier = modifier.padding(horizontal = 5.dp)
            )
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