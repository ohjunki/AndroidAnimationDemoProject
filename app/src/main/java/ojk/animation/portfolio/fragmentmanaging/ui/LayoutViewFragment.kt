package ojk.animation.portfolio.fragmentmanaging.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ojk.animation.portfolio.R
import ojk.animation.portfolio.navigationhelper.getTransitionView
import ojk.animation.portfolio.navigationhelper.pushNavigationBaseFragment
import ojk.animation.portfolio.utils.navigation.NavigationBaseFragment

class LayoutViewFragment : NavigationBaseFragment<Int>() {
  lateinit var root : View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedElementEnterTransition = AutoTransition()
    sharedElementReturnTransition = AutoTransition()
  }
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(R.layout.fragment_home, container, false)
    root.findViewById<TextView>(R.id.text_home).apply { text = "Number ${paramEntity ?: 0}" }
    root.findViewById<Button>(R.id.next).apply{
      text = "Go to ${ (paramEntity ?: 0) + 1 }"
      setOnClickListener {
        it.transitionName = "Home"
        root.findViewById<View>(R.id.test).transitionName = ""
        pushNavigationBaseFragment(LayoutViewFragment::class.java, arrayListOf( Pair( it, "Home")), (paramEntity ?: 0) + 1 )
      }
    }
    return root
  }
}