package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.ui.theme.SearchText
import com.kassaev.simbirsoft_1_git.ui.theme.SoftLight

@Composable
fun SearchField(
    selectedTabIndex: Int,
    value: String,
    setValue: (String) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        value = value,
        onValueChange = {
            setValue(it)
        },
        textStyle = TextStyle(
            color = SearchText,
            fontSize = 14.sp
        ),
        singleLine = true,
        decorationBox = { innerText ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SoftLight, RoundedCornerShape(4.dp))
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = stringResource(R.string.search),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    tint = SearchText
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(if (value.isEmpty()) 0.01F else 1F)
                ) {
                    innerText()
                }
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(if (selectedTabIndex == 0) R.string.search_placeholder_event else R.string.search_placeholder_npo),
                        color = SearchText
                    )
                }
            }
        }
    )
}
