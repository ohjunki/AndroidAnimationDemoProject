package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.ncapdevi.fragnav.FragNavTransactionOptions
import ojk.android.utils.DLog
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.ActivityBodyCheckDemoBinding
import ojk.animation.portfolio.utils.navigation.NavigationBaseActivity
import ojk.animation.portfolio.utils.navigation.NavigationBaseFragment
import java.io.Serializable
import java.util.*

class BodyCheckDemoActivity : NavigationBaseActivity(0, R.id.fragment_container) {
    lateinit var binding: ActivityBodyCheckDemoBinding
    override fun getRootFragment(index: Int) = BodyCheckMainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_body_check_demo);
    }

}

class BodyCheckDemoActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityBodyCheckDemoBinding

    val stack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_body_check_demo);
        pushNavigationBaseFragment<Void>( BodyCheckMainFragment::class.java , null , null )
    }

    fun <T:Any> pushNavigationBaseFragment(fragment: Class<*>, sharedElementList: List<Pair<View, String>>?, data : T? ) {
        val fragment  = fragment.getConstructor().newInstance() as Fragment
        data?.let {
            val bundle = Bundle()
            if( it is Serializable) {
                DLog.ee("Serializable")
                bundle.putSerializable(NavigationBaseFragment.PARAM_KEY, it)
            }else {
                bundle.putString(NavigationBaseFragment.PARAM_KEY, Gson().toJson(data))
                DLog.ee("Serializable no ${Gson().toJson(data)}")
            }
            bundle.putSerializable( NavigationBaseFragment.PARAM_CLASSTYPE_KEY, data.javaClass )
            fragment.arguments = bundle
        }



        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            if( stack.isNotEmpty() ) detach(stack.peek())
            add(R.id.fragment_container, fragment)
            sharedElementList?.forEach{  this.addSharedElement(it.first, it.second) }
            commit()
        }
        stack.add(fragment)
    }

    override fun onBackPressed() {
        if( supportFragmentManager.backStackEntryCount > 1 ){
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction().apply {
                detach(stack.pop())
                attach(stack.peek())
                commit()
            }
        }else
            super.onBackPressed()
    }
}