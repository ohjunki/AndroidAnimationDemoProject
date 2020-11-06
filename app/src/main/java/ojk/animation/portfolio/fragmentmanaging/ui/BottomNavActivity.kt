package ojk.animation.portfolio.fragmentmanaging.ui

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigation
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem
import ojk.animation.portfolio.R
import ojk.animation.portfolio.bodycheck.BodyCheckMainFragment
import ojk.animation.portfolio.utils.navigation.BottomTabBaseActivity

class BottomNavActivity : BottomTabBaseActivity(3,1) {
    override fun initBottomNavBar(btmNav: FluidBottomNavigation) {
        btmNav.accentColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        btmNav.backColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        btmNav.textColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        btmNav.iconColor = ContextCompat.getColor(this, R.color.black)
        btmNav.iconSelectedColor = ContextCompat.getColor(this, R.color.iconSelectedColor)

        btmNav.items = listOf(
                FluidBottomNavigationItem(
                    "Layout Base",
                    ContextCompat.getDrawable( this, R.drawable.btm_icon_1)),
                FluidBottomNavigationItem(
                    "Code Base",
                    ContextCompat.getDrawable( this, R.drawable.btm_icon_2)),
                FluidBottomNavigationItem(
                    "BodyCheck",
                    ContextCompat.getDrawable( this, R.drawable.btm_icon_3)))
    }

    override fun getRootFragment(index: Int): Fragment {
        return when(index){
            0 -> LayoutViewFragment()
            1 -> CodeViewFragment.newInstance(0)
            else -> BodyCheckMainFragment()
        }
    }
}


