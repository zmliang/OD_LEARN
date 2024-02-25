package com.zml.guide

import android.content.Context
import android.graphics.RectF
import android.view.View


interface IGuide{

    fun show()

    fun dismiss()

    fun context():Context

    fun getStepView():View?
    fun nextStep()

}

interface IGuiderLayer :IGuide{

    fun addTarget(view: View):IGuiderLayer

    fun setOnNextStepListener(listener: OnNextStepListener):IGuiderLayer

    fun setStepView(view: View):IGuiderLayer

    fun currentTargetRect():RectF

}

interface OnNextStepListener{
    fun onNext(index: Int,guideLayer: IGuiderLayer)
}