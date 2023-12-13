package com.pos.ui.kline

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

data class  KLineData(
                      val open: Float,
                      val high: Float,
                      val low: Float,
                      val close: Float)




fun generateKLineData(context: Context): List<KLineData> {
    // 返回默认json文件中配置的值
    val defaultStr = readAssets2String(context, "mock.json")
    if (!TextUtils.isEmpty(defaultStr)) {
        val gson = Gson()
        try {
            val obj = gson.fromJson(defaultStr, JsonObject::class.java)
            val columnObj: JsonArray = obj.getAsJsonArray("column") ?: return ArrayList()
            val columns: MutableList<String> = ArrayList()
            for (i in 0 until columnObj.size()) {
                columns.add(columnObj[i].asString)
            }
            val resultArray = JsonArray()
            val items = obj.getAsJsonArray("item")
            for (i in 0 until items.size()) {
                val sourceItem = items[i].asJsonArray
                val klineItem = JsonObject()
                for (j in columns.indices) {
                    klineItem.add(columns[j], sourceItem[j])
                }
                resultArray.add(klineItem)
            }
            val type = object : TypeToken<List<KLineData?>?>() {}.type
            return gson.fromJson(resultArray, type)
        } catch (e: JsonSyntaxException) {
            Log.e("KLineData:", "error")
        }
    }
    return ArrayList()
}

fun readAssets2String(context: Context, assetsFilePath: String?): String {
    if (assetsFilePath.isNullOrEmpty()) {
        return ""
    }
    try {
        val file = context.assets.open(assetsFilePath)
        val stringBuilder = StringBuilder()
        val inputStream = InputStreamReader(file)
        val buffer = BufferedReader(inputStream)
        var line: String?  = ""
        while (line != null ) {
            stringBuilder.append(line)
            line = buffer.readLine()
        }

        return stringBuilder.toString()
    } catch (var6: IOException) {
        var6.printStackTrace()
    }
    return ""
}
