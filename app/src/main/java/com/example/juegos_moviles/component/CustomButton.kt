package com.example.juegos_moviles.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.juegos_moviles.ui.theme.ButtonText
import com.example.juegos_moviles.ui.theme.DarkBackground
import com.example.juegos_moviles.ui.theme.MainColor

@Composable
fun CustomButton(
    description: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box {
        Box(
            modifier = Modifier
                .offset(6.dp, 6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DarkBackground)
                .matchParentSize()
        )
        Box(
            modifier = Modifier
                .background(
                    color = MainColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .fillMaxWidth()
                .padding(
                    vertical = 32.dp,
                    horizontal = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = description,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = ButtonText,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}