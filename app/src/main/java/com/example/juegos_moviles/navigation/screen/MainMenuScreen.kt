package com.example.juegos_moviles.navigation.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.juegos_moviles.component.CustomButton
import com.example.juegos_moviles.R
import com.example.juegos_moviles.ui.theme.DarkBackground
import com.example.juegos_moviles.ui.theme.MainColor

//@Preview(showBackground = true)
@Composable
fun MainMenuScreen(
    navController: NavController = rememberNavController()
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.icoin)
    )

    val fireAnimation = rememberInfiniteTransition(label = "fire")
    val fireAlpha by fireAnimation.animateFloat(
        initialValue = 1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fireAlpha"
    )
    val fireRotation by fireAnimation.animateFloat(
        initialValue = -30f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fireRotation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFC6FFF4)
            )
            .padding(
                vertical = 80.dp,
                horizontal = 40.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopEnd)
                    .offset(x=40.dp,y=32.dp)
            )
            Text(
                text = "¡JUEGOS VARIOS!",
                textAlign = TextAlign.Center,
                fontSize = 56.sp,
                fontWeight = FontWeight.Black,
                color = MainColor,
                letterSpacing = 4.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = DarkBackground,
                        offset = Offset(8f, 8f),
                        blurRadius = 0f
                    )
                )
            )
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopStart)
                    .offset(x=(-40).dp,y=32.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box {
                CustomButton(
                    "¡ESPADA, ESCUDO O DRAGÓN!",
                    {navController.navigate("eed")}
                    )
                Text(
                    "\uD83D\uDD25",
                    fontSize = 48.sp,
                    color = Color(0xFFFF9406).copy(alpha = fireAlpha),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(16.dp, 16.dp)
                        .rotate(fireRotation)
                )
            }
            CustomButton(
                "LOTERÍA",
                {navController.navigate("loteria")}
            )
            CustomButton(
                "ADIVINA EL NÚMERO",
                {navController.navigate("adivina")}
            )
        }
    }
}