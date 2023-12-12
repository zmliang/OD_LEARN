package com.pos.ui.widget

import android.util.Log
import android.view.ScaleGestureDetector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Float.max
import java.lang.Float.min
import kotlin.math.absoluteValue
import kotlin.random.Random


@Composable
fun _canvas(){

    KLineChartView(_sampleData, modifier = Modifier
        .fillMaxWidth()
        .height(300.dp))

}

val _random = Random(200)
var _sampleData = listOf(
    KLineData(1L, 100f, 120f, 80f, 110f),
    KLineData(2L, 110f, 130f, 90f, 120f),
    KLineData(3L, 120f, 140f, 100f, 130f),
    KLineData(4L, 130f, 150f, 110f, 140f),
    KLineData(5L, 140f, 160f, 120f, 150f),
    KLineData(5L, 140f, 160f, 120f, 120f),
    KLineData(5L, 120f, 120f, 160f, 110f),

    KLineData(1L, 100f, 120f, 80f, 110f),
    KLineData(2L, 110f, 130f, 90f, 120f),
    KLineData(3L, 120f, 140f, 100f, 130f),
    KLineData(4L, 130f, 150f, 110f, 140f),
    KLineData(5L, 140f, 160f, 120f, 150f),
    KLineData(5L, 140f, 160f, 120f, 120f),
    KLineData(5L, 120f, 120f, 160f, 110f),

    KLineData(1L, 100f, 120f, 80f, 110f),
    KLineData(2L, 110f, 130f, 90f, 120f),
    KLineData(3L, 120f, 140f, 100f, 130f),
    KLineData(4L, 130f, 150f, 110f, 140f),
    KLineData(5L, 140f, 160f, 120f, 150f),
    KLineData(5L, 140f, 160f, 120f, 120f),
    KLineData(5L, 120f, 120f, 160f, 110f),

    KLineData(1L, 100f, 120f, 80f, 110f),
    KLineData(2L, 110f, 130f, 90f, 120f),
    KLineData(3L, 120f, 140f, 100f, 130f),
    KLineData(4L, 130f, 150f, 110f, 140f),
    KLineData(5L, 140f, 160f, 120f, 150f),
    KLineData(5L, 140f, 160f, 120f, 120f),
    KLineData(5L, 120f, 120f, 160f, 110f),

    KLineData(1L, 100f, 120f, 80f, 110f),
    KLineData(2L, 110f, 130f, 90f, 120f),
    KLineData(3L, 120f, 140f, 100f, 130f),
    KLineData(4L, 130f, 150f, 110f, 140f),
    KLineData(5L, 140f, 160f, 120f, 150f),
    KLineData(5L, 140f, 160f, 120f, 120f),
    KLineData(5L, 120f, 120f, 160f, 110f)

    // Add more sample data as needed
)

class KLineChartModel(private val data: List<KLineData>) {
    val maxPrice: Float by lazy { data.maxOf { it.high } }
    val minPrice: Float by lazy { data.minOf { it.low } }

    var startIndex: Int by mutableIntStateOf(0)
    var endIndex: Int by mutableIntStateOf( kotlin.math.min(5,data.lastIndex))

    val visibleData: List<KLineData>
        get() = data.subList(startIndex, endIndex + 1)



    fun handleDragGesture(dragAmount: Offset) {

        val dragDirection = if (dragAmount.x > 0) {
            Log.i("zml","向优化")
            DragDirection.Right
        } else {
            Log.i("zml","向作画")
            DragDirection.Left
        }
        val CANDLE_WIDTH = 108f // 设置蜡烛图的宽度
        val SCREEN_SIZE = 1080 // 设置屏幕的宽度

        val dragDistance = dragAmount.x.absoluteValue

        val visibleDataCount = endIndex - startIndex + 1
        val maxVisibleDataCount = SCREEN_SIZE / CANDLE_WIDTH


        when (dragDirection) {
            DragDirection.Left -> {
                if (visibleDataCount < maxVisibleDataCount && endIndex < data.lastIndex) {
                    endIndex++
                } else if (startIndex > 0 && dragDistance >= SCREEN_SIZE) {
                    val moveCount = (dragDistance / CANDLE_WIDTH).toInt()
                    startIndex -= moveCount
                    endIndex = startIndex + visibleDataCount - 1
                }
            }
            DragDirection.Right -> {
                if (visibleDataCount < maxVisibleDataCount && startIndex > 0) {
                    startIndex--
                } else if (endIndex < data.lastIndex && dragDistance >= SCREEN_SIZE) {
                    val moveCount = (dragDistance / CANDLE_WIDTH).toInt()
                    endIndex += moveCount
                    startIndex = endIndex - visibleDataCount + 1
                }
            }
        }
    }


    fun updateVisibleData() {
        // 重新计算可见数据
        //val visibleData = data.subList(startIndex, endIndex + 1)

    }

    enum class DragDirection {
        Left, Right
    }
}


data class KLineData(val timestamp: Long,
                     val open: Float,
                     val high: Float,
                     val low: Float,
                     val close: Float)
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

    val kLineChartModel = remember { KLineChartModel(kLineDataList) }

    //val scaleGestureDetector = remember { ScaleGestureDetector(context, ScaleListener()) }

    val textMeasurer =  rememberTextMeasurer()

    Box(
        modifier = modifier
            .pointerInput(Unit){
                detectDragGestures{change, dragAmount ->
                    kLineChartModel.handleDragGesture(dragAmount)
                }
            }
//            .pointerInteropFilter { event ->
//
//                scaleGestureDetector.onTouchEvent(event)
//                true
//            }
            .graphicsLayer(
                scaleX = scaleX,
                scaleY = scaleY,
                translationX = translateX,
                translationY = translateY
            )
    ) {

        LaunchedEffect(kLineChartModel.startIndex, kLineChartModel.endIndex) {
            // 在 startIndex 或 endIndex 发生变化时重新计算可见数据并进行组件刷新
            kLineChartModel.updateVisibleData()
        }

        // 绘制蜡烛图
        Canvas(modifier = Modifier.fillMaxSize()) {
            //
            KLineChartGrid( maxPrice = kLineChartModel.maxPrice,
                minPrice = kLineChartModel.minPrice,
                textMeasurer = textMeasurer)
            // 绘制K线图逻辑
            drawKLineChart(kLineChartModel.visibleData)
        }

    }
}


fun DrawScope.KLineChartGrid(
    maxPrice: Float,
    minPrice: Float,
    textMeasurer:TextMeasurer
) {
    val priceRange = maxPrice - minPrice
    val priceStep = priceRange / 4

    // 绘制网格线
    for (i in 0..4) {
        val y = (1 - i / 4f) * size.height
        drawLine(
            start = Offset(0f, y),
            end = Offset(size.width, y),
            color = Color.LightGray,
            strokeWidth = 1.dp.toPx()
        )

        // 绘制价格标签
        val price = minPrice + i * priceStep
        drawText(
            textMeasurer = textMeasurer,
            text = "$price",
            style = TextStyle(fontSize = 12.sp),
            topLeft = Offset(0f, y - 6.dp.toPx())
        )
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