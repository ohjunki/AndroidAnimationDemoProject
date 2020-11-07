package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.BodyCheckDetailFragmentBinding
import ojk.animation.portfolio.utils.navigation.NavigationBaseFragment

class BodyCheckDetailFragment : NavigationBaseFragment<Int>() {
    lateinit var binding : BodyCheckDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<BodyCheckDetailFragmentBinding>(inflater, R.layout.body_check_detail_fragment, container, false)
        binding.lifecycleOwner = this
        binding.srcImg = paramEntity!!
        binding.time.transitionName = "TEST"
        return binding.root
    }

}