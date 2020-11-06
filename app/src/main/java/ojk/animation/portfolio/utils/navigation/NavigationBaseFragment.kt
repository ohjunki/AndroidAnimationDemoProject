package ojk.animation.portfolio.utils.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import ojk.android.utils.DLog

/**
 * parameter를 자동으로 넣고 뺄 수 있는
 */
@Suppress("UNCHECKED_CAST")
open class NavigationBaseFragment<T> : Fragment(){
    companion object{
        const val PARAM_KEY = "PARAM_KEY"
        const val PARAM_CLASSTYPE_KEY = "PARAM_CLASSTYPE_KEY"
    }
    var paramEntity : T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val className = getSerializable(PARAM_CLASSTYPE_KEY) as? Class<*>
            paramEntity = getSerializable(PARAM_KEY) as? T
            DLog.ee("HERE2 ${paramEntity}")
            if( paramEntity == null && className != null ){
                DLog.ee("HERE1 ${getString(PARAM_KEY)}")
                paramEntity = getString(PARAM_KEY)?.let { Gson().fromJson<T>(it, className) }
                DLog.ee("HERE")
            }
        }
    }
}