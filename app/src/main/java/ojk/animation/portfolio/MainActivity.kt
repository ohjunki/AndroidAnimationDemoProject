package ojk.animation.portfolio

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ojk.animation.portfolio.viewoverlay.FoldAnimationActivity
import ojk.animation.portfolio.bodycheck.BodyCheckDemoActivity
import ojk.animation.portfolio.youtubedemo.YouTubeDemoActivity

/**
 * Start from https://developer.android.com/training/constraint-layout/motionlayout/examples
 * Detail Information here https://www.notion.so/jkoh/Portfolio-Android-Animation-30c0cd9d247944d0aa903ae1c8818df2
 */
class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var doShowPaths = false

    /**
     * https://www.notion.so/jkoh/MotionLayout-ConstraintLayout-d04970dfab844f378aa0e2034c33c865
     * 위의 링크에 필요 내용을 정리했습니다.
     */
    private val dataset: Array<DemosAdapter.Demo> = arrayOf(
        DemosAdapter.Demo(
            "YouTube like motion Example",
            "Example showing a transition like YouTube",
            YouTubeDemoActivity::class.java
        ),
        DemosAdapter.Demo(
            "Folding Animation",
            " with ViewOverlay API",
            FoldAnimationActivity::class.java
        ),
        DemosAdapter.Demo("DrawerLayout Example With MotionLayout", "Advanced DrawerLayout with MotionLayout", R.layout.motion_13_drawerlayout),
        DemosAdapter.Demo("Enter Animation", "BodyCheck Animation", BodyCheckDemoActivity::class.java )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewManager = LinearLayoutManager(this)
        viewAdapter = DemosAdapter(dataset)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, value: Boolean) {
        doShowPaths = value
    }

    fun start(activity: Class<*>, layoutFileId: Int) {
        val intent = Intent(this, activity).apply {
            putExtra("layout_file_id", layoutFileId)
            putExtra("showPaths", doShowPaths)
        }
        startActivity(intent)
    }

}
