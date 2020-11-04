package ojk.animation.portfolio.utils

import android.animation.*
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat


internal fun View?.scaleAnimator(from: Float = this?.scaleX ?: 0f,
                                 to: Float,
                                 animationDuration: Long,
                                 animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@scaleAnimator?.scaleX = it.animatedValue as Float
                this@scaleAnimator?.scaleY = it.animatedValue as Float
            }
        }

internal fun View?.scaleXAnimator(from: Float = this?.scaleX ?: 0f,
                                  to: Float,
                                  animationDuration: Long,
                                  animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@scaleXAnimator?.scaleX = it.animatedValue as Float
            }
        }

internal fun View?.scaleYAnimator(from: Float = this?.scaleY ?: 0f,
                                  to: Float,
                                  animationDuration: Long,
                                  animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@scaleYAnimator?.scaleY = it.animatedValue as Float
            }
        }

internal fun View?.rotateAnimator(from: Float = this?.rotation ?: 0f,
                                  to: Float,
                                  animationDuration: Long,
                                  animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@rotateAnimator?.rotation = it.animatedValue as Float
            }
        }
internal fun View?.rotateXAnimator(from: Float = this?.rotationX ?: 0f,
                                  to: Float,
                                  animationDuration: Long,
                                  animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@rotateXAnimator?.rotationX = it.animatedValue as Float
            }
        }

internal fun View?.rotateYAnimator(from: Float = this?.rotationY ?: 0f,
                                  to: Float,
                                  animationDuration: Long,
                                  animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@rotateYAnimator?.rotationY = it.animatedValue as Float
            }
        }

internal fun View?.translationYAnimator(from: Float = 0f,
                                        to: Float,
                                        animationDuration: Long,
                                        animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@translationYAnimator?.translationY = it.animatedValue as Float
            }
        }
internal fun View?.translationXAnimator(from: Float = 0f,
                                        to: Float,
                                        animationDuration: Long,
                                        animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@translationXAnimator?.translationX = it.animatedValue as Float
            }
        }

internal fun View?.alphaAnimator(from: Float = 1f,
                                 to: Float,
                                 animationDuration: Long,
                                 animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofFloat(from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@alphaAnimator?.alpha = it.animatedValue as Float
            }
        }

internal fun ImageView?.tintAnimator(from: Int,
                                     to: Int,
                                     animationDuration: Long,
                                     animationInterpolator: Interpolator = LinearInterpolator()
) = ValueAnimator.ofObject(ArgbEvaluator(), from, to)
        .apply {
            duration = animationDuration
            interpolator = animationInterpolator
            addUpdateListener {
                this@tintAnimator?.setTintColor(it.animatedValue as Int)
            }
        }


internal fun ImageView.setTintColor(color: Int) =
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(color))


internal fun Animator.reversing(sequenceElseTogether : Boolean = true ) : Animator {
    return when (this) {
        is AnimatorSet -> {
            AnimatorSet().let { set->
                if( sequenceElseTogether ){
                    val list = mutableListOf<Animator>();
                    for ( i in childAnimations.size-1 downTo 0){
                        val anim = childAnimations[i].reversing(sequenceElseTogether)
                        if( i != childAnimations.size-1 ){
                            anim.startDelay = childAnimations[i+1].startDelay
                        }else{
                            anim.startDelay = 0
                        }
                        list.add(anim)
                    }
                    set.playSequentially( list )
                }else{
                    for ( i in childAnimations.size-1 downTo 0){
                        Log.e( "OJK", "sum ${childAnimations[i].duration + childAnimations[i].startDelay }")
                    }
                    val logestDuration = childAnimations.maxWith(Comparator { o1, o2 -> ((o1.duration + o1.startDelay) - (o2.duration + o2.startDelay)).toInt() })?.let { max-> max.duration+max.startDelay } ?: 0
                    val shortestDuration = childAnimations.maxWith(Comparator { o2, o1 -> ((o1.duration + o1.startDelay) - (o2.duration + o2.startDelay)).toInt() })?.let { max-> max.duration+max.startDelay } ?: 0
                    Log.e("OJK", "duration ${logestDuration} ${shortestDuration}")
                    set.playTogether( childAnimations.map { it.reversing( sequenceElseTogether ).apply { startDelay = logestDuration-(duration+startDelay) } })
                }
                set
            }
        }
        is ValueAnimator -> {
            val newAni = clone()
            newAni.interpolator =
                ReverseInterpolator(newAni.interpolator)
            newAni
        }
        else -> this.clone()
    }
}

class ReverseInterpolator @JvmOverloads constructor(delegate: TimeInterpolator = LinearInterpolator()) :
    android.view.animation.Interpolator {
    private val delegate: TimeInterpolator = delegate
    override fun getInterpolation(input: Float): Float {
        Log.e("OJK", "${input} ${delegate.getInterpolation(1-input)}")
        return delegate.getInterpolation(1-input)
    }
}