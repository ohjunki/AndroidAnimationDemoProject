package ojk.animation.portfolio.bodycheck

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ojk.animation.portfolio.R
import ojk.animation.portfolio.databinding.ActivityBodyCheckDemoBinding
import ojk.animation.portfolio.navigationhelper.SimpleNavigate


class BodyCheckDemoActivity : AppCompatActivity() , SimpleNavigate {
    lateinit var binding: ActivityBodyCheckDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityBodyCheckDemoBinding>(this, R.layout.activity_body_check_demo);
    }

    override fun navigate( fragment : Class<*>, shareElement : List<View>?) {
        val fragment : Fragment = supportFragmentManager.fragmentFactory.instantiate(
            fragment.classLoader!!,
            fragment.name
        )

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fragment_container, fragment )
        beginTransaction.addToBackStack(null);
        shareElement?.forEach{ v-> beginTransaction.addSharedElement(v, v.transitionName)}
        beginTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private val map = HashMap<String,Any>();
    override fun getBundle(): MutableMap<String, Any> = map
}