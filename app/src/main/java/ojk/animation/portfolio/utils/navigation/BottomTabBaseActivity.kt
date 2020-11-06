package ojk.animation.portfolio.utils.navigation

import android.os.Bundle
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigation
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener
import ojk.animation.portfolio.R

abstract class BottomTabBaseActivity(
    override val numberOfRootFragments: Int,
    private val firstTabIndex : Int = 0
) : NavigationBaseActivity( firstTabIndex, R.id.fragment_container_root),
    OnTabSelectedListener
{
    lateinit var navView: FluidBottomNavigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity_bottom_tab_nav)
        navView = findViewById(R.id.fluidBottomNavigation)
        navView.selectTab(firstTabIndex)
        initBottomNavBar(navView)
        navView.onTabSelectedListener = this
    }

    abstract fun initBottomNavBar( btmNav : FluidBottomNavigation)
    override fun onTabSelected(position: Int){
        selectTab(position);
    }

    private fun selectTab( index : Int ){
        mFragNavController.switchTab(index)
    }
}
