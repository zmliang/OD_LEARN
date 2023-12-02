package com.pos.ui.widget

import android.util.Log
import android.view.ScaleGestureDetector
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Float.max
import java.lang.Float.min
import kotlin.random.Random


@Composable
fun _canvas(){

    KLineChartView(_sampleData, modifier = Modifier.fillMaxSize())

}

val _random = Random(200)
var _sampleData = listOf(
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),
    KLineData(_random.nextFloat(), _random.nextFloat(), _random.nextFloat(), _random.nextFloat()),

    // Add more sample data as needed
)

data class KLineData(val open:Float,val close:Float,val high:Float,val low:Float)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KLineChartView(
    kLineDataList: List<KLineData>,
    modifier: Modifier = Modifier
) {
    var scaleX by remember { mutableStateOf(1f) }
    var scaleY by remember { mutableStateOf(1f) }
    var translateX by remember { mutableStateOf(0f) }
    var translateY by remember { mutableStateOf(0f) }
    val context = LocalContext.current

    val scaleGestureDetector = remember { ScaleGestureDetector(context, ScaleListener()) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                scaleGestureDetector.onTouchEvent(event)
                true
            }
            .graphicsLayer(
                scaleX = scaleX,
                scaleY = scaleY,
                translationX = translateX,
                translationY = translateY
            )
    ) {
        // 绘制K线图
        Canvas(modifier = Modifier.fillMaxSize()) {
            // 绘制K线图逻辑
            drawKLineChart(kLineDataList)
        }

    }
}
class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    private var scaleFactor = 1f

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        Log.i("zml","onScale==$detector")
        scaleFactor *= detector.scaleFactor
        scaleFactor = max(0.1f, min(scaleFactor, 10.0f)) // 设置缩放范围
        // 更新缩放比例
        return true
    }
}

fun DrawScope.drawKLineChart(kLineDataList: List<KLineData>) {
    val candleWidth = 10.dp.toPx() // K线蜡烛的宽度
    val chartPadding = 20.dp.toPx() // 图表的内边距

    val minValue = kLineDataList.minOf { it.low }
    val maxValue = kLineDataList.maxOf { it.high }

    val chartHeight = size.height - (2 * chartPadding)
    val chartWidth = size.width - (2 * chartPadding)

    val xStep = chartWidth / kLineDataList.size

    val yScale = chartHeight / (maxValue - minValue)

    kLineDataList.forEachIndexed { index, kLineData ->
        val x = chartPadding + index * xStep
        val yHigh = size.height - chartPadding - (kLineData.high - minValue) * yScale
        val yLow = size.height - chartPadding - (kLineData.low - minValue) * yScale
        val yOpen = size.height - chartPadding - (kLineData.open - minValue) * yScale
        val yClose = size.height - chartPadding - (kLineData.close - minValue) * yScale

        // 绘制K线蜡烛的高低线
        drawLine(
            color = Color.Black,
            start = Offset(x, yHigh),
            end = Offset(x, yLow),
            strokeWidth = 1.dp.toPx()
        )

        // 绘制K线蜡烛的开盘收盘矩形
        drawRect(
            color = if (kLineData.open > kLineData.close) Color.Red else Color.Green,
            topLeft = Offset(x - candleWidth / 2, yOpen),
            size = Size(candleWidth, yClose - yOpen)
        )
    }
}