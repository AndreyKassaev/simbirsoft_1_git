package com.kassaev.simbirsoft_1_git.core.uikit

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kassaev.simbirsoft_1_git.core.R
import com.kassaev.simbirsoft_1_git.core.navigation.Router
import com.kassaev.simbirsoft_1_git.core.ui.theme.BlueGrey
import com.kassaev.simbirsoft_1_git.core.ui.theme.DarkSlateBlue
import com.kassaev.simbirsoft_1_git.core.ui.theme.Leaf
import com.kassaev.simbirsoft_1_git.core.ui.theme.White
import com.kassaev.simbirsoft_1_git.core.util.Event
import com.kassaev.simbirsoft_1_git.core.util.timestamFormatter

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun EventCard(
    event: Event,
    setIsWatched: (String) -> Unit = {},
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .alpha(if (event.isWatched) 0.3F else 1F)
            .clickable {
                setIsWatched(event.id)
                navController.navigate(
                    Router.EventDetail(eventId = event.id)
                )
            }
    ) {
        AsyncImage(
            model = event.imageUrlList.firstOrNull(),
            contentDescription = event.description,
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(300.dp),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter)
                .blur(16.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White),
                        startY = 0f,
                        endY = 300f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = event.title,
                fontSize = 21.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = BlueGrey,
                fontWeight = FontWeight.Black
            )
            Image(
                painter = painterResource(R.drawable.event_decoration),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
            Text(
                text = event.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = DarkSlateBlue,
                fontSize = 15.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Leaf)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
                Text(
                    text = timestamFormatter(event.timestamp),
                    color = White,
                    fontSize = 17.sp
                )
            }
        }
    }
}
