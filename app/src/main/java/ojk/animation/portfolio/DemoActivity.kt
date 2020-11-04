package ojk.animation.portfolio

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ojk.animation.portfolio.navigationhelper.SimpleNavigate


@RequiresApi(Build.VERSION_CODES.LOLLIPOP) // for View#clipToOutline
class DemoActivity : AppCompatActivity(), SimpleNavigate {
    private lateinit var container: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra("layout_file_id", 0)
        setContentView(layout)
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

