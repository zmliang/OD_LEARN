package com.pos.ui.widget

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun _canvas(){

    KLineChartView()

}


var _sampleData = listOf(
    KLineData(100f, 150f, 80f, 200f),
    KLineData(160f, 140f, 120f, 180f),
    KLineData(135f, 170f, 100f, 200f),
    KLineData(100f, 150f, 80f, 200f),
    KLineData(160f, 140f, 120f, 180f),
    KLineData(135f, 170f, 100f, 200f),
    KLineData(100f, 150f, 80f, 200f),
    KLineData(160f, 140f, 120f, 180f),
    KLineData(135f, 170f, 100f, 200f),
    KLineData(100f, 150f, 80f, 200f),
    KLineData(160f, 140f, 120f, 180f),
    KLineData(135f, 170f, 100f, 200f),
    KLineData(100f, 150f, 80f, 200f),
    KLineData(160f, 140f, 120f, 180f),
    KLineData(135f, 170f, 100f, 200f),
    // Add more sample data as needed
)

data class KLineData(val open:Float,val close:Float,val high:Float,val low:Float)
@Composable
fun KLineChartView(
    //stockDataLoad:StockDataLoader,
    initialVisibleCount:Int = 50,
    initialScaleX:Float = 1f,
    initialScaleY:Float = 1f,
){
    var scaleX by remember { mutableFloatStateOf(initialScaleX) }
    var scaleY by remember { mutableFloatStateOf(initialScaleY) }

    var translateX by remember { mutableFloatStateOf(0f) }
    var translateY by remember { mutableFloatStateOf(0f) }

    var visibleCount by remember { mutableIntStateOf(initialVisibleCount) }
    var kLineDataList by remember { mutableStateOf(_sampleData) }

    val scope = rememberCoroutineScope()

    suspend fun loadData(){
        withContext(Dispatchers.IO){
            // val newdata = stockDataLoad.load(0,visibleCount)
            kLineDataList = _sampleData
        }
    }

    LaunchedEffect(key1 = "", block ={
        //loadData()
    } )



    val kLineChartWidth = 800*scaleX
    val kLineChartHeight = 400*scaleY

    val kLineChartMinX = -translateX/scaleY
    val kLineChartMaxX = kLineChartMinX+visibleCount.toFloat()

    val kLineChartMinY = kLineDataList.map { it.low }.minOrNull()?:0f
    val kLineChartMaxY = kLineDataList.map { it.high }.minOrNull()?:0f

    val kLineChartScaleX = kLineChartWidth/(kLineChartMaxX-kLineChartMinX)
    val kLineChartScaleY = kLineChartHeight/(kLineChartMaxY-kLineChartMinY)

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scaleX *= zoom
                scaleY *= zoom

                translateX += pan.x / scaleX
                translateY -= pan.y / scaleY

                scaleX = scaleX.coerceIn(0.5f, 2f)
                scaleY = scaleY.coerceIn(0.5f, 2f)

                translateX = translateX.coerceIn(-visibleCount.toFloat(), 0f)
                translateY = translateY.coerceIn(-kLineChartHeight, 0f)

            }
        }
        .clipToBounds()
        .background(Color.White)
    ){
        drawKlineChart(
            kLineDataList,
            kLineChartMinX,
            kLineChartMaxX,
            kLineChartMinY,
            kLineChartMaxY,
            kLineChartScaleX,
            kLineChartScaleY,
            translateX,
            translateY,
            kLineChartWidth,
            kLineChartHeight
        )
    }


//    LaunchedEffect(key1 = visibleCount){
//        if (visibleCount<stockDataLoad.totalCount){
//            withContext(Dispatchers.IO){
//                val
//            }
//        }
//    }


    LaunchedEffect(scaleX,scaleY,translateX,translateY ){
        animate(
            initialValue = 0f,
            targetValue = visibleCount.toFloat(),
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        ){value,value1 ->
            visibleCount = value.toInt()
        }
    }

}

@Composable
fun drawKlineChart(
    kLineDataList:List<KLineData>,
    _minX:Float,
    _maxX:Float,
    _minY:Float,
    _maxY:Float,
    _scaleX:Float,
    _scaleY:Float,
    _transleteX:Float,
    _transleteY:Float,
    width:Float,
    height:Float
){
    Canvas(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer(
            scaleX = _scaleX,
            scaleY = _scaleY,
            translationX = _transleteX,
            translationY = _transleteY
        )
    ){

        val candleWidth = 10.dp.toPx()
        val candleSpacing = 2.dp.toPx()

        drawRect(
            color = Color.White,
            size = Size(width,height)
        )

        for (i in kLineDataList.indices){
            val data = kLineDataList[i]
            val x = ((i.toFloat()+_minX)*_scaleX)
            val candleHeight = (data.high-data.low)*_scaleY
            val candleTop = height - ((data.high-_minY)*_scaleY)
            val candleBottom = height - ((data.low-_minY)*_scaleY)

            drawRect(
                color=if (data.open<data.close)Color.Green else Color.Red,
                topLeft = Offset(x,candleTop),
                size = Size(candleWidth,candleHeight)
            )

            drawRect(
                color=if (data.open<data.close)Color.Green else Color.Red,
                topLeft = Offset(x+candleSpacing,candleBottom),
                size = Size(candleWidth-candleSpacing*2, 1.dp.toPx())
            )

            drawRect(
                color=if (data.open<data.close)Color.Green else Color.Red,
                topLeft = Offset(x+candleSpacing,candleTop),
                size = Size( 1.dp.toPx(),candleHeight)
            )

            drawRect(
                color=if (data.open<data.close)Color.Green else Color.Red,
                topLeft = Offset(x+candleWidth-candleSpacing*2,candleTop),
                size = Size( 1.dp.toPx(),candleHeight)
            )

        }
    }
}