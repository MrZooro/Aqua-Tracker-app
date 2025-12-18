package com.example.aquatracker.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.aquatracker.R
import com.example.aquatracker.util.NumberFormatter
import com.example.aquatracker.viewModel.dataClass.AquaItem
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    actionWidth: Dp = 96.dp,
    data: AquaItem,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {

    val actionWidthPx = with(LocalDensity.current) {
        actionWidth.toPx()
    }

    val swipeState = rememberSwipeableState(
        initialValue = SwipeCardState.CLOSED
    )

    val anchors = mapOf(
        0f to SwipeCardState.CLOSED,
        -actionWidthPx to SwipeCardState.OPEN
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
    ) {

        Row(
            modifier = Modifier.matchParentSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onEdit, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }

        Card(
            modifier = Modifier
                .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = data.date, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.aqua_value_label) + data.value
                )
                Text(
                    text = stringResource(R.string.aqua_change_lable) + NumberFormatter.formatDelta(
                        data.delta
                    )
                )
            }
        }
    }
}

enum class SwipeCardState() {
    CLOSED,
    OPEN
}
