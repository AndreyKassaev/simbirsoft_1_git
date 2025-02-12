package com.kassaev.simbirsoft_1_git.UiKit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.DialogDivider
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.White

@Composable
fun ProfileDialog(
    setIsDialogOpen: (Boolean) -> Unit,
    setPhoto: (String?) -> Unit
){
    Dialog(
        onDismissRequest = {
            setIsDialogOpen(false)
        }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 30.dp),
                text = stringResource(R.string.choose_avatar),
                color = CharcoalGrey,
            )
            HorizontalDivider(modifier = Modifier.height(1.dp), color = DialogDivider)
            Text(
                modifier = Modifier
                    .clickable {
                        setIsDialogOpen(false)
//                                choosePhoto()
                    }
                    .fillMaxWidth()
                    .padding(12.dp),
                text = stringResource(R.string.choose_photo),
                color = Leaf,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(modifier = Modifier.height(1.dp), color = DialogDivider)
            Text(
                modifier = Modifier
                    .clickable {
                        setIsDialogOpen(false)
//                                makePhoto()
                    }
                    .fillMaxWidth()
                    .padding(12.dp),
                text = stringResource(R.string.make_photo),
                color = Leaf,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(modifier = Modifier.height(1.dp), color = DialogDivider)
            Text(
                modifier = Modifier
                    .clickable {
                        setIsDialogOpen(false)
                        setPhoto(null)
                    }
                    .fillMaxWidth()
                    .padding(12.dp),
                text = stringResource(R.string.delete),
                color = Leaf,
                textAlign = TextAlign.Center
            )
        }
    }
}
