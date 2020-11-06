package ojk.animation.portfolio

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


@RequiresApi(Build.VERSION_CODES.LOLLIPOP) // for View#clipToOutline
class DemoActivity : AppCompatActivity() {
    private lateinit var container: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra("layout_file_id", 0)
        setContentView(layout)
    }
}

