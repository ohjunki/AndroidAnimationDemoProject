package ojk.animation.portfolio.bodycheck

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        sharedElementEnterTransition = AutoTransition()
        sharedElementReturnTransition = AutoTransition()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<BodyCheckDetailFragmentBinding>(inflater, R.layout.body_check_detail_fragment, container, false)
        binding.vm = viewModel
        binding.srcImg = getShareMap()!!["resourceId"] as Int
        return binding.root
    }

    class BodyCheckDetailViewModel : ViewModel() {
    }
}