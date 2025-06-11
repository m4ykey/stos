package com.m4ykey.stos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.m4ykey.stos.app.App
import com.m4ykey.stos.owner.domain.model.BadgeCounts
import com.m4ykey.stos.question.domain.model.QuestionOwner
import com.m4ykey.stos.question.presentation.components.BadgeRow
import com.m4ykey.stos.question.presentation.detail.DisplayOwner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayOwnerPreview() {
    val badgeCounts = BadgeCounts(
        gold = 134,
        silver = 1,
        bronze = 3
    )
    val item = QuestionOwner(
        displayName = "Test name",
        link = "",
        reputation = 134334234,
        userId = 132,
        profileImage = "https://lumiere-a.akamaihd.net/v1/images/character_themuppets_kermit_b77a431b.jpeg?region=0%2C0%2C450%2C450",
        badgeCounts = badgeCounts
    )
    DisplayOwner(
        item = item,
        onOwnerClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun BadgeRowPreview() {
    val badgeCounts = BadgeCounts(
        bronze = 123,
        silver = 123,
        gold = 123
    )
    BadgeRow(
        badgeCounts = badgeCounts
    )
}
