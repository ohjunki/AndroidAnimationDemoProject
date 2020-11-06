package ojk.animation.portfolio.utils.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import java.lang.Exception


abstract class NavigationBaseActivity(
    private val firstFragmentIndex : Int = 0,
    @IdRes private val containerId: Int,
    override val numberOfRootFragments : Int = 1
) : AppCompatActivity(),
    FragmentNavigation,
    FragNavController.RootFragmentListener {

    override val mFragNavController = FragNavController( supportFragmentManager, containerId )
    override var popFragNavTransactionOptions: FragNavTransactionOptions? = null

    abstract override fun getRootFragment(index: Int): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragNavController()
    }

    private fun initFragNavController() {
        mFragNavController.apply {
            rootFragmentListener = this@NavigationBaseActivity
            initialize(firstFragmentIndex)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mFragNavController.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if((mFragNavController.currentFrag as? BackPressControllableFragment)?.onBackPressed() != true){
            try{
                if( mFragNavController.popFragment(popFragNavTransactionOptions).not() ){
                    super.onBackPressed()
                }
                popFragNavTransactionOptions = null
            }catch (e: Exception){
                super.onBackPressed()
            }
        }
    }
}