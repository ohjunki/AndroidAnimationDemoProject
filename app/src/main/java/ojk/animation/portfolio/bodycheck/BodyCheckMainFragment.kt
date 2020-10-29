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

class BodyCheckMainFragment : Fragment() , ViewTreeObserver.OnGlobalLayoutListener{

    companion object {
        fun newInstance() = BodyCheckMainFragment()
    }

    private var isGlobalLoaded : Boolean = false;
    private var binding: BodyCheckMainFragmentBinding? = null
    val viewModel: BodyCheckMainViewModel by viewModels<BodyCheckMainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSubscription()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null;
        Log.e( "MotionLayout", "onDestroyView called" )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e( "MotionLayout", "onCreateView called" )
        binding = binding ?: DataBindingUtil.inflate<BodyCheckMainFragmentBinding>(inflater,R.layout.body_check_main_fragment, container, false)
        if( !isGlobalLoaded ){
            binding!!.root.viewTreeObserver.addOnGlobalLayoutListener(this)
//            binding.motionLayout.progress = 1.0F
        }
        return binding!!.root
    }

    override fun onGlobalLayout() {
        binding!!.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
//        binding.motionLayout.transitionToStart()
        isGlobalLoaded = true;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding!!.vm = viewModel
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