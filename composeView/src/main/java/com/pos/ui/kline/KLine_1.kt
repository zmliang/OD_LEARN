package com.pos.ui.kline

import android.graphics.Paint.Style
import android.text.TextPaint
import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pos.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt


private const val DIVIDER_COUNT = 4
private const val SCALE_DEFAULT = 20F
private const val SCALE_MAX = 25F
private const val SCALE_MIN = 3F

private const val SCALE_STEP = 0.3f
private val CANDLE_WIDTH = 0.5.dp
private val CANDLE_SPACE = 0.1.dp

private val CHART_HEIGHT = 300.dp

private val CHART_PADDING = 10.dp



@Composable
fun KLineView(dataList:List<KLineData>){

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth()) {
        //ChartHeadView()
        KLineChart(dataList = dataList)
    }

}

@Composable
fun KLineChart(dataList:List<KLineData>){
    if (dataList.isEmpty()){
        return
    }
    var width = 0f
    var height = CHART_HEIGHT.value
    var downX = 0f

    var startIndex by remember { mutableIntStateOf(0) }
    var endIndex by remember { mutableIntStateOf(dataList.size-1) }

    var showCross by remember{ mutableStateOf(false)}
    // 十字光标x轴坐标
    var crossX by remember { mutableFloatStateOf(0f) }
    // 十字光标y轴坐标
    var crossY by remember { mutableFloatStateOf(0f) }

    var scale by remember{ mutableFloatStateOf(SCALE_DEFAULT) }

    val velocityTracker = remember { VelocityTracker() }
    var flingVelocity by remember { mutableFloatStateOf(0f) }//速度
    var continueScroll by remember { mutableStateOf(false) }//是否继续滑动
    val coroutineScope = rememberCoroutineScope()
    val animationSpec: AnimationSpec<Float> = tween(durationMillis = 1500)

    var candleWidth = 0f
    var candleSpace = 0f
    var count = 0
    var yMaxValue = 0f
    var yMinValue = 0f
    var maxValue = 0f
    var minValue = 0f
    var yInterval = 0f
    var yValueInterval = 0f

    val yValuePaint = TextPaint()
    yValuePaint.isAntiAlias = true
    yValuePaint.textSize = LocalContext.current.resources.getDimension(com.google.android.material.R.dimen.m3_snackbar_margin)

    val crossPaint = Paint()
    crossPaint.isAntiAlias = true
    crossPaint.style = PaintingStyle.Stroke
    crossPaint.strokeWidth = 2f
    crossPaint.color = Color.Black

    val candlePaint = Paint()
    candlePaint.isAntiAlias = true
    candlePaint.strokeWidth = 1f
    // 最大值，最小值，横线长度
    val lineWidth = 20.dp.value

    val framePaint = Paint()
    framePaint.isAntiAlias = true
    framePaint.style = PaintingStyle.Stroke
    framePaint.strokeWidth = 1f
    framePaint.color = Color(0xffAAAAAA)


    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(CHART_HEIGHT)
        .padding(CHART_PADDING)
        .pointerInput(Unit){
            detectTransformGestures(
                onGesture = { centroid, pan, gestureZoom, gestureRotate ->
                    //Log.i("zml","detectTransformGestures: centroid=$centroid   ,  pan=$pan   ,  gestureZoom=$gestureZoom   ,  gestureRotate=$gestureRotate")
                    //Log.i("zml","detectTransformGestures: pan=$pan， centroid=$centroid")
                   if (showCross){
                       crossX = centroid.x
                       crossY = centroid.y
                   }
                    //位移
                    val dx = centroid.x - downX
                    count = (-dx / (candleWidth + candleSpace)).toInt()
                    if (kotlin.math.abs(count) >= 1) {
                        startIndex += count
                        endIndex += count
                        downX = centroid.x
                        if (startIndex < 0) {
                            endIndex += kotlin.math.abs(startIndex)
                            startIndex = 0
                        }
                        if (endIndex > dataList.size - 1) {
                            startIndex += endIndex - dataList.size
                            endIndex = dataList.size - 1
                        }
                    }

                    //缩放
                    if (gestureZoom<1){
                        scale -= SCALE_STEP*gestureZoom
                    }else if (gestureZoom>1){
                        scale += SCALE_STEP*gestureZoom
                    }

                    if (scale > SCALE_MAX) {
                        scale = SCALE_MAX
                    }
                    if (scale < SCALE_MIN) {
                        scale = SCALE_MIN
                    }
                })
        }
//        .pointerInput(Unit){
//            detectDragGestures(
//                onDragStart = { velocityTracker.resetTracking() },
//                onDragEnd = {
//                    val velocity = velocityTracker.calculateVelocity().x
//                    flingVelocity = velocity
//
//                    Log.e("zml","手指离开时的速度 = ${velocity.absoluteValue}")
////                    if (velocity.absoluteValue >= 500f) {
////                        continueScroll = true
////                        coroutineScope.launch {
////                            // 继续滑动一段距离
////                            awaitAnimationFrame()
////                            onFling()
////                            awaitAnimationFrame()
////                            continueScroll = false
////                        }
////                    }
//                }
//            ) { change, dragAmount ->
//                velocityTracker.addPosition(
//                    change.uptimeMillis,
//                    Offset(dragAmount.x,dragAmount.y)
//                )
//                //change.consume()
//            }
//        }
        .pointerInput(Unit) {
            awaitEachGesture {
                while (true) {
                    val event = awaitPointerEvent()

                    event.changes.forEach {
                        if (it.changedToDown()){
                            velocityTracker.resetTracking()
                            downX = it.position.x
                            showCross = true
                            crossX=downX
                            crossY = it.position.y
                        }else if (it.changedToUp()){
                            val velocity = velocityTracker.calculateVelocity().x
                            flingVelocity = velocity
                            Log.e("zml","手指离开时的速度 = ${velocity.absoluteValue}")
                            if (velocity.absoluteValue >= 500f) {
                                continueScroll = true
                                coroutineScope.launch {
                                    val inertiaDistance = calculateInertiaDistance(velocity)
                                    val targetDistance = inertiaDistance.coerceIn(-500f, 500f)
                                    val animatedDistance = animate(
                                        initialValue = 0f,
                                        targetValue = targetDistance,
                                        animationSpec = animationSpec
                                    ){f1,f2 ->

                                    }
                                    //onFling()
                                    // 等待滑动动画完成
                                    delay(1500)
                                    continueScroll = false
                                }
                            }

                            if (showCross) {
                                showCross = false
                            }
                        }else{
                            velocityTracker.addPosition(
                            it.uptimeMillis,
                            Offset(it.position.x,it.position.y)
                            )
                        }
                    }
            }
        }
    }
        .drawWithContent {
            drawContent()
            if (showCross){
                drawIntoCanvas {
                    val priceStr = yToPrice(crossY, yMaxValue, yMinValue, height).toString()
                    val textWidth = yValuePaint.measureText(priceStr)
                    // 绘制十字光标
                    it.drawLine(Offset(0f, crossY), Offset(width - textWidth, crossY), crossPaint)
                    it.drawLine(Offset(crossX, 0f), Offset(crossX, height), crossPaint)
                    // 绘制交叉线上的价格
                    yValuePaint.color = Color.Blue.toArgb()
                    it.nativeCanvas.drawText(priceStr, width - textWidth, crossY, yValuePaint)
                }
            }
        }
    ){
        width = drawContext.size.width
        height = drawContext.size.height
        // y轴等分高度
        yInterval = height / DIVIDER_COUNT
        // 蜡烛宽度
        candleWidth = CANDLE_WIDTH.toPx() * scale
        // 蜡烛间隙
        candleSpace = CANDLE_SPACE.toPx() * scale
        // 当前画布能够放置蜡烛的数量
        count = (width / (candleSpace + candleWidth)).toInt()

        startIndex = endIndex - count
        if (startIndex < 0) {
            startIndex = 0
            endIndex = count
        }
        // 计算当前画布中的最高股价和最低股价
        maxValue = dataList.maxOf { it.high }
        minValue = dataList.minOf { it.low }

        // y轴最大坐标，最小坐标 与最大价格/最小价格流出间距，用于给最大值和最小值流出绘制空间
        yMaxValue = maxValue + getOffset(maxValue)
        yMinValue = minValue - getOffset(minValue)

        // 股价等分间隔
        yValueInterval = (yMaxValue - yMinValue) / DIVIDER_COUNT

        drawIntoCanvas {
            // 1.绘制边框及绘制y轴等分线
            it.drawRect(0f, 0f, width, height, framePaint)
            it.drawLine(Offset(0f, yInterval), Offset(width, yInterval), framePaint)
            it.drawLine(Offset(0f, yInterval * 2), Offset(width, yInterval * 2), framePaint)
            it.drawLine(Offset(0f, yInterval * 3), Offset(width, yInterval * 3), framePaint)

            // 2.绘制柱状图及上下阴线
            var startX = 0f
            //趋势线
            val path = Path()
            val path2 = Path()
            var lastPoint: Offset? = null

            for (i in startIndex until endIndex) {
                if (dataList[i].close  > dataList[i].open) {
                    candlePaint.color = Color.Red
                    candlePaint.style = PaintingStyle.Fill
                } else {
                    candlePaint.color = Color.Green
                    candlePaint.style = PaintingStyle.Fill
                }
                // 绘制矩形
                var offset = 0f
                if (dataList[i].close == dataList[i].open) offset = 0.1f // 开盘价等收盘价，绘制一个0.1px的实线
                var candleTop = priceToY(dataList[i].close + offset, yMaxValue, yMinValue, height)
                var candleBottom = priceToY(dataList[i].open, yMaxValue, yMinValue, height)
                it.drawRect(startX, candleTop, startX + candleWidth, candleBottom, candlePaint)

                //贝塞尔曲线
                val x = startX+candleWidth/2
                val y = candleTop
                lastPoint?.let { p ->
                    run {
                        val endPoint = Offset(x, y)
                        val controller1 = Offset(
                            x = p.x + (endPoint.x - p.x) / 2F,
                            y = p.y,
                        )
                        val controller2 = Offset(
                            x = p.x + (endPoint.x - p.x) / 2F,
                            y = endPoint.y,
                        )

                        path.also {
                            run {
                                it.cubicTo(
                                    x1 = controller1.x,
                                    y1 = controller1.y,
                                    x2 = controller2.x,
                                    y2 = controller2.y,
                                    x3 = endPoint.x,
                                    y3 = endPoint.y,
                                )
                            }
                        }
                        path2.also {
                            run {
                                it.quadraticBezierTo(
                                    controller1.x,
                                    controller1.y+100,
                                    endPoint.x,
                                    endPoint.y+100)
                            }
                        }

                    }
                }

                lastPoint = Offset(x, y)
                if (i == startIndex) {
                    path.moveTo(x, y)
                    path2.moveTo(x,y+100)
                }


                // 绘制上阴线
                it.drawLine(
                    Offset(startX + candleWidth / 2,
                        priceToY(Math.max(dataList[i].open, dataList[i].close), yMaxValue, yMinValue, height)),
                    Offset((startX + candleWidth / 2),
                        priceToY(dataList[i].high, yMaxValue, yMinValue, height)),
                    candlePaint)


                // 绘制下阴线
                it.drawLine(
                    Offset(startX + candleWidth / 2,
                        priceToY(Math.min(dataList[i].open, dataList[i].close), yMaxValue, yMinValue, height)),
                    Offset((startX + candleWidth / 2),
                        priceToY(dataList[i].low, yMaxValue, yMinValue, height)),
                    candlePaint)
                // 标示最大值和最小值
                if (dataList[i].high == maxValue) {
                    val maxValueLength = yValuePaint.measureText(maxValue.toString())
                    if (startX + (candleWidth / 2) + lineWidth + maxValueLength <= width) {
                        // 未超出边界，再进行绘制
                        candlePaint.color = Color.Black
                        it.drawLine(
                            Offset(startX + (candleWidth / 2),
                                priceToY(dataList[i].high, yMaxValue, yMinValue, height)),
                            Offset(startX + (candleWidth / 2) + lineWidth,
                                priceToY(dataList[i].high, yMaxValue, yMinValue, height)), candlePaint);
                        it.nativeCanvas.drawText(
                            numToString(maxValue), startX + (candleWidth / 2) + lineWidth,
                            priceToY(dataList[i].high, yMaxValue, yMinValue, height) + 3.dp.toPx(), yValuePaint)
                    }
                } else if (dataList[i].low == minValue) {
                    val minValueLength = yValuePaint.measureText(minValue.toString())
                    if (startX + (candleWidth / 2) + lineWidth + minValueLength <= width) {
                        // 未超出边界，再进行绘制
                        candlePaint.color = Color.Black
                        it.drawLine(
                            Offset(startX + (candleWidth / 2), priceToY(dataList[i].low, yMaxValue, yMinValue, height)),
                            Offset(startX + (candleWidth / 2) + lineWidth, priceToY(dataList[i].low,yMaxValue, yMinValue, height)),
                            candlePaint)
                        it.nativeCanvas.drawText(
                            numToString(minValue), startX + (candleWidth / 2) + lineWidth,
                            priceToY(dataList[i].low, yMaxValue, yMinValue, height), yValuePaint)
                    }
                }

                //绘制底部矩形
                it.drawRect(startX,
                    height,
                      startX + candleWidth,
                    (height-(kotlin.math.abs(candleBottom - candleTop)*0.8f)),
                            //priceToY(dataList[i].open, yMaxValue, yMinValue, height),
                    candlePaint)

                startX += candleWidth + candleSpace
            }

            // 3.绘制y轴坐标
            yValuePaint.color = Color.Black.toArgb()
            it.nativeCanvas.drawText(numToString(yMaxValue), 0f, yValuePaint.textSize, yValuePaint)
            it.nativeCanvas.drawText(numToString(yMaxValue - yValueInterval), 0f, yInterval + yValuePaint.textSize, yValuePaint)
            it.nativeCanvas.drawText(numToString(yMaxValue - yValueInterval * 2), 0f, yInterval * 2 + yValuePaint.textSize, yValuePaint)
            it.nativeCanvas.drawText(numToString(yMaxValue - yValueInterval * 3), 0f, yInterval * 3 + yValuePaint.textSize, yValuePaint)
            it.nativeCanvas.drawText(numToString(yMinValue), 0f, height, yValuePaint)

            // 4.趋势线
            drawPath(
                path = path,
                style = Stroke(0.5.dp.toPx()),
                color = Color.Black
            )

            drawPath(
                path = path2,
                style = Stroke(0.5.dp.toPx()),
                color = Color.Blue
            )
        }



    }
}

private fun calculateInertiaDistance(velocity: Float): Float {
    val decelerationRate = 0.85f // 减速率，可根据需要调整

    return (velocity * velocity) / (2 * decelerationRate)
}

private fun buildCurveLine(path: Path, startPoint: Offset, endPoint: Offset) {
    val controller1 = Offset(
        x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
        y = startPoint.y,
    )
    val controller2 = Offset(
        x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
        y = endPoint.y,
    )
    path.cubicTo(
        x1 = controller1.x,
        y1 = controller1.y,
        x2 = controller2.x,
        y2 = controller2.y,
        x3 = endPoint.x,
        y3 = endPoint.y,
    )
}

fun yToPrice(f: Float, yMaxValue: Float, yMinValue: Float, height: Float): Float {
    return (((yMaxValue - f * (yMaxValue - yMinValue) / height) * 100).roundToInt() / 100).toFloat()
}


fun getOffset(value: Float): Float {
    val str = value.toString()
    return if (str.contains(".")) {
        val firstStr = str.subSequence(0, str.indexOf("."))
        getOffsetValue(firstStr.toString())
    } else {
        getOffsetValue(str)
    }
    return 0f
}

private fun getOffsetValue(str: String): Float {
    return if (str.length > 3) {
        30f
    } else if (str.length > 1) {
        3f
    } else {
        0.3f
    }
}

fun priceToY(f: Float, yMaxValue: Float, yMinValue: Float, height: Float): Float {
    return ((((yMaxValue - f) / (yMaxValue - yMinValue) * height) * 100).roundToInt() / 100).toFloat()
}

fun numToString(f: Float): String {
    val format = DecimalFormat("0.00")
    return format.format(f)
}
