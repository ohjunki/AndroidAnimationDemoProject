package ojk.animation.portfolio.viewoverlay

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.graphics.Interpolator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.databinding.DataBindingUtil
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.ActivityFoldAnimationBinding

class FoldAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFoldAnimationBinding>(this, R.layout.activity_fold_animation)

        arrayListOf(binding.lb,binding.rb,binding.lt,binding.rt).forEach {
            it.pivotX = it.top.toFloat()
            it.setOnClickListener { target ->
                val parent = target.parent as ViewGroup
                binding.container.overlay.add(target)
                val set : AnimatorSet = AnimatorInflater.loadAnimator(baseContext, R.animator.bottom_close_up ) as AnimatorSet
                set.interpolator = AccelerateDecelerateInterpolator()
                set.duration = 2000
                set.setTarget(target)
                set.addListener(onEnd = {
                    target.rotationX = 0F
                    target.alpha = 1F
                    binding.container.overlay.remove(target)
                    parent.addView(target)
                })
                set.start()
            }}
    }
}