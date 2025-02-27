package com.kassaev.simbirsoft_1_git.screen.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kassaev.simbirsoft_1_git.R
import com.kassaev.simbirsoft_1_git.UiKit.TextField
import com.kassaev.simbirsoft_1_git.UiKit.TextFieldPassword
import com.kassaev.simbirsoft_1_git.navigation.LocalNavController
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.ui.theme.CharcoalGrey
import com.kassaev.simbirsoft_1_git.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.ui.theme.White
import com.kassaev.simbirsoft_1_git.util.GetTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    setTopAppBar: (@Composable (() -> Unit)) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(Unit) {
        setTopAppBar {
            GetTopAppBar(
                title = stringResource(R.string.authorization),
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            (context as? android.app.Activity)?.finishAffinity()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    }

    val isValid by viewModel.getIsValidFlow().collectAsStateWithLifecycle()
    val credentials by viewModel.getCredentialsFlow().collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
            .background(White)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(R.string.auth_via_socials),
                fontSize = 15.sp,
                color = CharcoalGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.vk),
                    contentDescription = stringResource(R.string.vk),
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(40.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = stringResource(R.string.facebook),
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .size(40.dp),
                )
                Icon(
                    painter = painterResource(R.drawable.ok),
                    contentDescription = stringResource(R.string.ok),
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(40.dp),
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.auth_via_app),
                fontSize = 15.sp,
                color = CharcoalGrey,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = credentials.email,
                onValueChange = viewModel::setEmail,
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.enter_email),
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextFieldPassword(
                value = credentials.password,
                onValueChange = viewModel::setPassword,
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.enter_password),
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    navController.navigate(
                        Router.Help
                    ) {
                        launchSingleTop = true
                        popUpTo(Router.Authorization) { inclusive = true }
                    }
                },
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Leaf
                )
            ) {
                Text(
                    text = stringResource(R.string.enter),
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = Leaf,
                    fontSize = 15.sp
                )
                Text(
                    text = stringResource(R.string.registration),
                    color = Leaf,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AuthorizationScreenPrev() {
//    AuthorizationScreen()
}