package ojk.animation.portfolio.utils.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import ojk.animation.portfolio.navigationhelper.getAllSharedElements
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
        initFragNavController( savedInstanceState )
    }

    private fun initFragNavController(savedInstanceState: Bundle?) {
        mFragNavController.apply {
            rootFragmentListener = this@NavigationBaseActivity
            initialize(firstFragmentIndex, savedInstanceState)
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
                mFragNavController.currentFrag?.let{ frag->
                    if( frag is SharedElementOnReturnProvider ){
                        frag.sharedElementsOnReturn
                            ?.forEach { options.addSharedElement(it) }
                            ?: frag.getAllSharedElements().forEach { options.addSharedElement(it) }
                    }
                    /*** Transition Name이 비어있지 않는 모든 View를 찾아서 넘겨준다.*/
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