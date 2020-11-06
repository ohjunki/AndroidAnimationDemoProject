package com.tenclouds.fluidbottomnavigation

import android.animation.AnimatorSet
import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.tenclouds.fluidbottomnavigation.extension.translationYAnimator
import kotlinx.android.synthetic.main.item.view.*


object FluidUtils{
    val forword = mutableMapOf<String, AnimatorSet>();
    val backword = mutableMapOf<String, AnimatorSet>();
    fun animateSelectItemView( view:View, isReverse : Boolean ){
        val key = view.tag as String
        val animator = forword[key] ?: view.animateSelectItemView().apply { forword[key] = this }
        if( animator.isStarted ){
            animator.pause()
            animator.interpolator = if( isReverse ) ReverseInterpolator(LinearInterpolator()) else LinearInterpolator()
            animator.resume()
        }else{
            animator.interpolator = if( isReverse ) ReverseInterpolator(LinearInterpolator()) else LinearInterpolator()
            animator.start()
        }
    }

    fun animateDeSelectItemView( view:View ){
        val key = view.tag as String
        val animator = forword[key] ?: view.animateSelectItemView().apply { forword[key] = this }
        if( animator.isRunning ){
            animator.pause()
            val interpol = animator.interpolator ?: LinearInterpolator()
            animator.interpolator = ReverseInterpolator(interpol)
            animator.resume()
            animator.addListener { animator.interpolator = interpol }
        }else{
            val ani = backword[key] ?: view.animateDeselectItemView().apply { backword[key] = this }
            ani.start()
        }
    }

    internal class ReverseInterpolator @JvmOverloads constructor(delegate: TimeInterpolator = LinearInterpolator()) : Interpolator {
        private val delegate: TimeInterpolator = delegate
        override fun getInterpolation(input: Float): Float {
            return 1-delegate.getInterpolation(input)
        }
    }
}
internal fun View.animateSelectItemView() =
        AnimatorSet()
                .apply {
                    playTogether(
                            circle.selectAnimator,
                            icon.selectAnimator,
                            title.selectAnimator,
                            rectangle.selectAnimator,
                            topContainer.selectAnimator)
                }


internal fun View.animateDeselectItemView() =
        AnimatorSet()
                .apply {
                    playTogether(
                            circle.deselectAnimator,
                            icon.deselectAnimator,
                            title.deselectAnimator,
                            rectangle.deselectAnimator,
                            topContainer.deselectAnimator)
                }

internal fun View.animateShow() =
        AnimatorSet()
                .apply {
                    play(translationYAnimator(
                            height.toFloat(),
                            0f,
                            3 * KEY_FRAME_IN_MS,
                            LinearOutSlowInInterpolator()))
                }
                .start()

internal fun View.animateHide() =
        AnimatorSet()
                .apply {
                    play(translationYAnimator(
                            0f,
                            height.toFloat(),
                            3 * KEY_FRAME_IN_MS,
                            LinearOutSlowInInterpolator()))
                }
                .start()