package com.sudarshan.dailynotes.presentation.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    notesCount: Int = 0,
    category: String? = null
) {
    Card(
        modifier = modifier

    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Text(text = notesCount.toString(), color = Color.Black.copy(alpha = 0.40f))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = category.toString(), color = Color.Black)
            Spacer(modifier = Modifier.size(10.dp))
            Divider(
                modifier = Modifier
                    .height(2.dp)
                    .background(color = Color.Black)
            )
        }
    }
}