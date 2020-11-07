package ojk.animation.portfolio.utils.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import ojk.android.utils.DLog
import ojk.animation.portfolio.fragmentmanaging.ui.LayoutViewFragment
import ojk.animation.portfolio.navigationhelper.getAllSharedElements
import ojk.animation.portfolio.navigationhelper.getTransitionView
import java.lang.Exception


abstract class NavigationBaseActivity(
    private val firstFragmentIndex : Int = 0,
    @IdRes private val containerId: Int,
    override val numberOfRootFragments : Int = 1
) : AppCompatActivity(),
    FragmentNavigation,
    FragNavController.RootFragmentListener {

    override val mFragNavController = FragNavController( supportFragmentManager, containerId )

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
                val options = FragNavTransactionOptions.newBuilder()
                options.reordering = true
                mFragNavController.currentFrag?.let{
                    /*** Transition Name이 비어있지 않는 모든 View를 찾아서 넘겨준다.*/
                    frag-> frag.getAllSharedElements().forEach { options.addSharedElement(it) }
                }
                if( mFragNavController.popFragment(options.build()).not() ){
                    super.onBackPressed()
                }
            }catch (e: Exception){
                super.onBackPressed()
            }
        }
    }
}