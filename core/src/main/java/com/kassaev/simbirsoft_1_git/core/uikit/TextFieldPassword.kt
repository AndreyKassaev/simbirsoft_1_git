package com.kassaev.simbirsoft_1_git.core.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.CharcoalGreyLight
import com.kassaev.simbirsoft_1_git.core.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.core.ui.theme.LightGreyTwo

@Composable
fun TextFieldPassword(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Leaf,
        backgroundColor = Leaf
    )
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
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
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(0.9F),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
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
                        IconButton(
                            modifier = Modifier
                                .padding(PaddingValues(0.dp))
                                .offset(x = 12.dp)
                                .weight(0.1F)
                                .height(24.dp),
                            onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }
                        ) {
                            Icon(
                                painter = painterResource(if(isPasswordVisible) R.drawable.hide_password else R.drawable.show_password),
                                contentDescription = stringResource(if(isPasswordVisible) R.string.hide_password else R.string.show_password)
                            )
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
private fun TextFieldPasswordPrev() {
    TextFieldPassword(
        label = "label",
        value = "",
        onValueChange = {},
        placeholder = "placeholder"
    )
}