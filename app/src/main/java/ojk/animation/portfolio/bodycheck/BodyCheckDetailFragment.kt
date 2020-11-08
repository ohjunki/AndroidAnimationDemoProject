package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.BodyCheckDetailFragmentBinding

class BodyCheckDetailFragment : Fragment() {

    companion object {
        fun newInstance() = BodyCheckDetailFragment()
    }

    val viewModel: BodyCheckDetailViewModel by viewModels()
    lateinit var binding : BodyCheckDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<BodyCheckDetailFragmentBinding>(inflater, R.layout.body_check_detail_fragment, container, false)
        binding.vm = viewModel
        binding.srcImg = getShareMap()!!["resourceId"] as Int
        binding.faceImage.viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                startPostponedEnterTransition()
                binding.faceImage.viewTreeObserver.removeOnPreDrawListener(this)
                return true;
            }
        })
        return binding.root
    }

    class BodyCheckDetailViewModel : ViewModel() {
    }
}