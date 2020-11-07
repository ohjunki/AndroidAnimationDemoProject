package ojk.animation.portfolio.utils.navigation

import android.view.View

/**
 * BackPress를 처리해야하는 Fragment일 경우에 구현하면 해당 Fragment의 backPress를 처리해준다.
 */
internal interface BackPressControllableFragment {
    fun onBackPressed() : Boolean
}

/**
 *
 */
internal interface SharedElementOnReturnProvider {
    val sharedElementsOnReturn : List<Pair<View, String>>?
        get() = null
}
