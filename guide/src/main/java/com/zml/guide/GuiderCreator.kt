package com.zml.guide

import android.content.Context
import com.zml.guide.GuideLayer.Companion.attach


 class GuiderCreator {
    companion object{
        fun create(context: Context):IGuiderLayer{
            return attach(context)
        }
    }


}