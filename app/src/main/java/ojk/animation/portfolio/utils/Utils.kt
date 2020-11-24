package ojk.animation.portfolio.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children


fun View.getViewStackTreeString( deepCnt : Int = 0 ) : String {
    var prefix = ""
    repeat( deepCnt ) {
        prefix += "  "
    }
    return (this as? ViewGroup)
        ?.let {
            var result = prefix + this.javaClass.simpleName
            it.children.forEach {  result +=  "\n" + it.getViewStackTreeString( deepCnt+1) }
            result
        }
        ?: prefix + this.javaClass.simpleName
}