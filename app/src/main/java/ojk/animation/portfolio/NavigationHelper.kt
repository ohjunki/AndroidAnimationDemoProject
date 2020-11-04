package ojk.animation.portfolio.navigationhelper

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment

fun Fragment.navigate(fragment : Class<*>, shareElement : List<View>? = null ) = (parentFragment as? SimpleNavigate)?.navigate(fragment,shareElement) ?: (activity as? SimpleNavigate)?.navigate(fragment,shareElement)
fun Fragment.getShareMap() = (parentFragment as? SimpleNavigate)?.getBundle() ?: (activity as? SimpleNavigate)?.getBundle()
fun Fragment.getTransitionView(transitionName : String, root : View? = this.view ) : View? {
    return root?.let {
        if( it.transitionName == transitionName) it
        else (it as? ViewGroup)
            ?.children?.map{ child-> getTransitionView(transitionName, child) }?.find { childView-> childView != null }
    }
}

internal interface SimpleNavigate{
    fun getBundle() : MutableMap<String,Any>
    fun navigate( fragment : Class<*>, shareElement : List<View>?)
}

interface BackPressControlableFragment {
    fun onBackPressed() : Boolean
}