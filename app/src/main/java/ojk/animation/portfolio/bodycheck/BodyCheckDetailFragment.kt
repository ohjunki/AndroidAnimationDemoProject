package ojk.animation.portfolio.bodycheck

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
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
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = AutoTransition()
        sharedElementReturnTransition = AutoTransition()
        viewModel.goBack.observe(this, Observer {
            findNavController().navigateUp()
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        binding = DataBindingUtil.inflate<BodyCheckDetailFragmentBinding>(inflater, R.layout.body_check_detail_fragment, container, false)
        binding.vm = viewModel
        binding.srcImg = getShareMap()!!["resourceId"] as Int
        binding.faceImage.viewTreeObserver.addOnPreDrawListener (object:ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                startPostponedEnterTransition()
                binding.faceImage.viewTreeObserver.removeOnPreDrawListener ( this )
                return true
            }
        });
        return binding.root
    }

    class BodyCheckDetailViewModel : ViewModel() {
        val goBack = MutableLiveData<Boolean>()
        fun onBack(){
            goBack.value = true
        }
    }
}