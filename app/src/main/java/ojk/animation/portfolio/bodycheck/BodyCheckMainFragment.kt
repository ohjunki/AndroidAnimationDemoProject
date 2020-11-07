package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionInflater
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
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.BodyCheckMainFragmentBinding
import ojk.animation.portfolio.navigationhelper.*
import ojk.animation.portfolio.utils.navigation.NavigationBaseFragment

class BodyCheckMainFragment : NavigationBaseFragment<Void>() {
    private lateinit var binding: BodyCheckMainFragmentBinding
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
        binding.lifecycleOwner = this
        binding.vm = viewModel

        binding.rootContainer.viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                binding.rootContainer.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true;
            }

        })
        return binding.root
    }

    private fun initSubscription() {
        viewModel.toastMsg.observe( this, Observer {
        })
        viewModel.toDetail.observe( this, Observer {
            val list = arrayListOf( Pair( getTransitionView( it.toString() )!!, it.toString())  )
            list.add( Pair( getTransitionView( "bg$it" )!!, "bg$it" ))
            pushNavigationBaseFragment( BodyCheckDetailFragment::class.java , list.toList() , it)
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