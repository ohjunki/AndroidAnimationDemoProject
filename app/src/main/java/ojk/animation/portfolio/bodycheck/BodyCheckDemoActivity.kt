package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.ActivityBodyCheckDemoBinding
import ojk.animation.portfolio.utils.navigation.NavigationBaseActivity

class BodyCheckDemoActivity : NavigationBaseActivity(0, R.id.fragment_container) {
    lateinit var binding: ActivityBodyCheckDemoBinding
    override fun getRootFragment(index: Int) = BodyCheckMainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_body_check_demo);
    }
}