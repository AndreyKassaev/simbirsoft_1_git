package com.kassaev.simbirsoft_1_git.UiKit

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.DialogDivider
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.createImageUri

@Composable
fun ProfileDialog(
    setIsDialogOpen: (Boolean) -> Unit,
    setPhoto: (String?) -> Unit,
) {
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                setPhoto(photoUri.toString())
                setIsDialogOpen(false)
            } else {
                setIsDialogOpen(false)
                Toast.makeText(context, R.string.oops, Toast.LENGTH_SHORT).show()
            }
        }
    )
    val permissions = mutableListOf(
        Manifest.permission.CAMERA,
    )
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    } else {
        permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        if (permissionsResult.all { it.value }) {
            createImageUri(context).also { uri ->
                photoUri = uri
                cameraLauncher.launch(uri)
            }
        } else {
            setIsDialogOpen(false)
            Toast.makeText(context, R.string.oops, Toast.LENGTH_SHORT).show()
        }
    }
    Dialog(
        onDismissRequest = {
            if (!isLoading) setIsDialogOpen(false)
        }
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Leaf
            )
        } else {
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
                            isLoading = true
                            launcher.launch(permissions.toTypedArray())
                        }
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = stringResource(R.string.take_photo),
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
}
