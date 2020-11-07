package ojk.animation.portfolio.navigationhelper

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.ncapdevi.fragnav.FragNavTransactionOptions
import ojk.android.utils.DLog
import ojk.animation.portfolio.bodycheck.BodyCheckDemoActivity
import ojk.animation.portfolio.utils.navigation.NavigationBaseFragment
import ojk.animation.portfolio.utils.navigation.FragmentNavigation
import java.io.Serializable

/**
 * 현재 fragment View들 전체를 돌아보면서 transitionName을 가진 View를 찾는 로직
 */
fun Fragment.getTransitionView(transitionName : String, root : View? = this.view ) : View? {
    return root?.let {
        if( it.transitionName == transitionName) it
        else (it as? ViewGroup)
            ?.children?.map{ child-> getTransitionView(transitionName, child) }?.find { childView-> childView != null }
    }
}

/**
 * transitionName이 비어있지 않은 모든 set일 가져온다.
 */
fun Fragment.getAllSharedElements(root : View? = this.view ) : MutableList<Pair<View, String>> {
    val list = mutableListOf<Pair<View,String>>()
    root?.let {
        DLog.ee(it.transitionName)
        if( it.transitionName != null && it.transitionName != "" ){
            list.add(Pair(it , it.transitionName ))
        }
        (it as? ViewGroup)?.children?.forEach{ child-> list.addAll(getAllSharedElements(child)) }
    }
    return list
}


fun Fragment.pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>? = null)
        = (requireActivity() as? FragmentNavigation)?.pushFragment(fragment,sharedElementList)
fun FragmentNavigation.pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>? ) {
    val options = FragNavTransactionOptions.newBuilder()
    options.reordering = true
    sharedElementList?.let {
        it.forEach { pair ->
            options.addSharedElement(pair)
        }
    }
    mFragNavController.pushFragment(fragment, options.build())
}

fun <T: Any> Fragment.pushNavigationBaseFragment(fragment: Class<*>, sharedElementList: List<Pair<View, String>>? = null, data : T? = null) = (requireActivity() as? FragmentNavigation)?.pushNavigationBaseFragment(fragment,sharedElementList, data )
fun <T: Any> FragmentNavigation.pushNavigationBaseFragment(fragment: Class<*>, sharedElementList: List<Pair<View, String>>?, data : T? ) {
    val fragment  = fragment.getConstructor().newInstance() as Fragment
    data?.let {
        val bundle = Bundle()
        if( it is Serializable ) {
            bundle.putSerializable(NavigationBaseFragment.PARAM_KEY, it)
        }else {
            bundle.putString(NavigationBaseFragment.PARAM_KEY, Gson().toJson(data))
        }
        bundle.putSerializable( NavigationBaseFragment.PARAM_CLASSTYPE_KEY, data.javaClass )
        fragment.arguments = bundle
    }

    val options = FragNavTransactionOptions.newBuilder()
    options.reordering = true
    sharedElementList?.let {
        it.forEach { pair ->
            options.addSharedElement(pair)
        }
    }
    mFragNavController.pushFragment(fragment, options.build())
}