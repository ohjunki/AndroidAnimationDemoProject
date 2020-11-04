package ojk.animation.portfolio.fragmentmanaging.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ojk.animation.portfolio.R
import ojk.animation.portfolio.bodycheck.BodyCheckMainFragment
import ojk.animation.portfolio.fragmentmanaging.ui.dashboard.DashboardFragment
import ojk.animation.portfolio.fragmentmanaging.ui.home.HomeFragment
import ojk.animation.portfolio.fragmentmanaging.ui.notifications.NotificationsFragment

class BottomNavActivity : AppCompatActivity() {
    var curTabIndex = 0
    val list = arrayListOf(
        StackableFragment(HomeFragment::class.java),
        StackableFragment(DashboardFragment::class.java),
        StackableFragment(BodyCheckMainFragment::class.java)
    )

    val listAdded = arrayListOf( false, false , false );

    lateinit var navView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        navView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener {
            selectTab(
                when( it.itemId ){
                R.id.navigation_home -> 0
                R.id.navigation_dashboard -> 1
                R.id.navigation_notifications -> 2
                else -> 0
            })
            true
        }
        selectTab(0)
    }

    private fun selectTab( index : Int ){
        supportFragmentManager.beginTransaction().apply {
            if( listAdded[curTabIndex] ) detach(list[curTabIndex])
            if( listAdded[index] ) attach(list[index]) else add(R.id.fragment_container_root, list[index])
            listAdded[index] = true
            curTabIndex = index
            commit()
        }
    }

    override fun onBackPressed() {
        if( !list[curTabIndex].onBackPressed() ){
            if( curTabIndex != 0 ) navView.selectedItemId = R.id.navigation_home
            else
                super.onBackPressed()
        }
    }
}

fun Fragment.generateTag() = javaClass.name