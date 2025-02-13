package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGreyLight
import com.kassaev.simbirsoft_1_git.ui.theme.DividerGrey
import com.kassaev.simbirsoft_1_git.util.toDateString
import com.kassaev.simbirsoft_1_git.util.toTimestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onBirthDateSelected: (String) -> Unit,
    birthDate: String,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = birthDate.toTimestamp()
    )
    var isDatePickerDialogOpen by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .clickable {
                isDatePickerDialogOpen = !isDatePickerDialogOpen
            }
    ) {
        Text(
            text = stringResource(R.string.birth_date),
            fontSize = 13.sp,
            color = CharcoalGreyLight,
            lineHeight = 13.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = birthDate,
                fontSize = 17.sp,
                color = CharcoalGrey,
            )
            Icon(
                painter = painterResource(R.drawable.calendar),
                contentDescription = stringResource(R.string.calendar)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp),
            color = DividerGrey
        )
        if (isDatePickerDialogOpen) {
            DatePickerDialog(
                onDismissRequest = { isDatePickerDialogOpen = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { selectedDate ->
                                onBirthDateSelected(selectedDate.toDateString())
                            }
                            isDatePickerDialogOpen = false
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isDatePickerDialogOpen = false
                        }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            ) {
                DatePicker(
                    showModeToggle = false,
                    state = datePickerState,
                )
            }
        }
    }
}
