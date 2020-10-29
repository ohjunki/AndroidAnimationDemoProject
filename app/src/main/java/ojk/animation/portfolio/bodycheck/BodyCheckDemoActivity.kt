package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.ActivityBodyCheckDemoBinding


class BodyCheckDemoActivity : AppCompatActivity() , SimpleNavigate{
    lateinit var binding: ActivityBodyCheckDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityBodyCheckDemoBinding>(this, R.layout.activity_body_check_demo);
    }

    override fun navigate(directinos : NavDirections, shareElement : List<View>?) {
        val extras = FragmentNavigator.Extras.Builder()
        shareElement?.forEach{ v-> extras.addSharedElement(v, v.transitionName)}
        binding.root.findNavController().navigate(
//            R.id.bodyCheckDetailFragment,
//            null,
//            NavOptions.Builder()
//                .build(),
            directinos,
            extras.build())
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        if( supportFragmentManager.backStackEntryCount == 0 )
//        else supportFragmentManager.popBackStack()
    }

    private val map = HashMap<String,Any>();
    override fun getBundle(): MutableMap<String, Any> = map

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}

fun Fragment.onBackPressed() = (activity as? SimpleNavigate)?.onBackPressed()
fun Fragment.navigate( directinos : NavDirections , shareElement : List<View>? = null ) = (activity as? SimpleNavigate)?.navigate(directinos,shareElement)
fun Fragment.getShareMap() = (activity as? SimpleNavigate)?.getBundle()
fun Fragment.getTransitionView( transitionName : String , root : View? = this.view ) : View? {
    return root?.let {
        if( it.transitionName == transitionName) it
        else (it as? ViewGroup)
            ?.children?.map{ child-> getTransitionView(transitionName, child) }?.find { childView-> childView != null }
    }
}

interface SimpleNavigate{
    fun onBackPressed()
    fun getBundle() : MutableMap<String,Any>
    fun navigate( directinos : NavDirections, shareElement : List<View>?)
}