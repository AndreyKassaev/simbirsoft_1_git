package com.kassaev.simbirsoft_1_git.core.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.ui.theme.LightGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGreyLight

@Composable
fun SearchResultMeta(
    keywordList: String,
    amount: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrey)
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.keywords) + ": ",
                fontWeight = FontWeight.SemiBold,
                color = CharcoalGrey
            )
            Text(
                text = keywordList,
                color = CharcoalGreyLight,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = stringResource(R.string.search_results) + ": ",
                fontWeight = FontWeight.SemiBold,
                color = CharcoalGrey
            )
            Text(
                text = amount,
                color = CharcoalGreyLight
            )
        }
    }
}
