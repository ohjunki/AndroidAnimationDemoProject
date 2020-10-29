package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.ActivityBodyCheckDemoBinding


class BodyCheckDemoActivity : AppCompatActivity() , SimpleNavigate{
    lateinit var binding: ActivityBodyCheckDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityBodyCheckDemoBinding>(this, R.layout.activity_body_check_demo);
    }

    override fun navigate( fragment : Class<*>, shareElement : List<View>?) {
        val fragment : Fragment = supportFragmentManager.fragmentFactory.instantiate(
            fragment.classLoader!!,
            fragment.name
        )

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.addToBackStack(null);
        beginTransaction.replace(R.id.fragment_container, fragment )
        shareElement?.forEach{ v-> beginTransaction.addSharedElement(v, v.transitionName)}
        beginTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private val map = HashMap<String,Any>();
    override fun getBundle(): MutableMap<String, Any> = map
}

fun Fragment.onBackPressed() = (activity as? SimpleNavigate)?.onBackPressed()
fun Fragment.navigate( fragment : Class<*> , shareElement : List<View>? = null ) = (activity as? SimpleNavigate)?.navigate(fragment,shareElement)
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
    fun navigate( fragment : Class<*>, shareElement : List<View>?)
}