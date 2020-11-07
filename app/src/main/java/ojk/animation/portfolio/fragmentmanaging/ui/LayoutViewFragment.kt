package ojk.animation.portfolio.fragmentmanaging.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ojk.animation.portfolio.R
import ojk.animation.portfolio.navigationhelper.pushNavigationBaseFragment
import ojk.animation.portfolio.utils.navigation.BackPressControllableFragment
import ojk.animation.portfolio.utils.navigation.NavigationBaseFragment
import ojk.animation.portfolio.utils.navigation.SharedElementOnReturnProvider


class LayoutViewFragment : NavigationBaseFragment<Int>(), BackPressControllableFragment, SharedElementOnReturnProvider {
  lateinit var root : View
  var t1Name = "Home"
  var t2Name = ""
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedElementEnterTransition = AutoTransition()
    sharedElementReturnTransition = AutoTransition()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    root = inflater.inflate(R.layout.fragment_home, container, false)
    setTransitionName()
    root.findViewById<TextView>(R.id.text_home).apply { text = "Number ${paramEntity ?: 0}" }
    root.findViewById<Button>(R.id.next).apply{
      transitionName = t2Name
      text = "Go to ${ (paramEntity ?: 0) + 1 }"
      setOnClickListener {
        t2Name = "Home"
        it.transitionName = t2Name
        t1Name = ""
        root.findViewById<View>(R.id.test).transitionName = t1Name
        pushNavigationBaseFragment(LayoutViewFragment::class.java, arrayListOf( Pair( it, "Home")), (paramEntity ?: 0) + 1 )
      }
    }
    return root
  }

  private fun setTransitionName(){
      root.findViewById<View>(R.id.test).apply { transitionName = t1Name }
      root.findViewById<Button>(R.id.next).apply { transitionName = t2Name }
    }

  override fun onBackPressed(): Boolean {
    /**
     * 버튼을 한번이라도 클릭한 경우 Transition Name이 바뀌기 때문에 backpress시에 transition이 제대로 동작하지 않는다. 원래대로 복구필요
     */
    t1Name = "Home"
    t2Name = ""
    setTransitionName()
    sharedOnReturn.add(Pair(root.findViewById(R.id.test),t1Name))
    return false
  }

  private val sharedOnReturn = mutableListOf<Pair<View,String>>()
  override val sharedElementsOnReturn: List<Pair<View, String>>?
    get() = sharedOnReturn
}