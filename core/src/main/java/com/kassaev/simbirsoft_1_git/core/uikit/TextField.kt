package com.kassaev.simbirsoft_1_git.core.uikit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGreyLight
import com.kassaev.simbirsoft_1_git.core.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.core.ui.theme.LightGreyTwo

@Composable
fun TextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Leaf,
        backgroundColor = Leaf
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(
                fontSize = 17.sp,
                color = CharcoalGrey
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            cursorBrush = SolidColor(Leaf),
            decorationBox = { innerText ->
                Column {
                    Text(
                        text = label,
                        fontSize = 13.sp,
                        color = CharcoalGreyLight,
                        lineHeight = 13.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = LightGreyTwo,
                                    fontSize = 20.sp
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp)
                            ) {
                                innerText()
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .height(1.dp),
                        color = DividerGrey
                    )
                }
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TextFieldPrev() {
    TextField(
        label = "label",
        value = "",
        onValueChange = {},
        placeholder = "placeholder"
    )
}