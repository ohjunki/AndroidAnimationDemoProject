package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import com.google.android.material.snackbar.Snackbar
import ojk.android.utils.DLog
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.BodyCheckMainFragmentBinding

class BodyCheckMainFragment : Fragment() {

    companion object {
        fun newInstance() = BodyCheckMainFragment()
    }

    private var binding: BodyCheckMainFragmentBinding? = null
    val viewModel: BodyCheckMainViewModel by viewModels<BodyCheckMainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSubscription()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        binding = DataBindingUtil.inflate<BodyCheckMainFragmentBinding>(inflater,R.layout.body_check_main_fragment, container, false)
        binding!!.vm = viewModel
        binding!!.motionLayout.viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                startPostponedEnterTransition()
                binding!!.motionLayout.viewTreeObserver.removeOnPreDrawListener(this);
                return true;
            }
        })
        return binding!!.root
    }

    private fun initSubscription() {
        viewModel.toastMsg.observe( this, Observer {
        })
        viewModel.toDetail.observe( this, Observer {
            getShareMap()!!["resourceId"] = it;
            val list = getTransitionView( it.toString() )?.let {  arrayListOf( it ) }
            getTransitionView( "bg$it" )?.let{ list?.add(it) }

            navigate( BodyCheckMainFragmentDirections.actionBodyCheckMainFragmentToBodyCheckDetailFragment() , list )
        })
    }


    class BodyCheckMainViewModel : ViewModel() {
        private val _toast = MutableLiveData<String>();
        private val _detailIndex = MutableLiveData<Int>();
        val toastMsg : LiveData<String> = _toast
        val toDetail : LiveData<Int> = _detailIndex

        fun onSymptopmpsClick( resourceId : Int){
            _detailIndex.value = resourceId;
        }

        fun onRequirementClick( index : Int){
            when(index){
                0->{ _toast.value = "Mask" }
                1->{ _toast.value = "Gloves" }
                2->{ _toast.value = "Alcohol" }
                3->{ _toast.value = "Soap" }
            }
        }
    }
}