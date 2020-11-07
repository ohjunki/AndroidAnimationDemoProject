package ojk.animation.portfolio.utils.navigation

import android.os.Bundle
import android.transition.AutoTransition
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import ojk.android.utils.DLog

/**
 * parameter를 자동으로 넣고 뺀다.
 * shared element transition 고려되어 있다.
 * @see
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
        /**
         * FragNav 라이브러리는 attach detach를 사용하기 떄문에 onBackPress시에도 enter Transition이 호출된다.
         */
        sharedElementEnterTransition = AutoTransition()

        arguments?.apply {
            val className = getSerializable(PARAM_CLASSTYPE_KEY) as? Class<*>
            paramEntity = getSerializable(PARAM_KEY) as? T
            if( paramEntity == null && className != null ){
                paramEntity = getString(PARAM_KEY)?.let { Gson().fromJson<T>(it, className) }
            }
        }
    }
}