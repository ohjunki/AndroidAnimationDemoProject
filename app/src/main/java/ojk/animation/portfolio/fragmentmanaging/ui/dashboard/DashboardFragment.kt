package ojk.animation.portfolio.fragmentmanaging.ui.dashboard

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ojk.animation.portfolio.R
import ojk.animation.portfolio.navigationhelper.navigate

class DashboardFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return LinearLayout(requireContext()).apply {
      layoutParams = ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
      gravity = Gravity.CENTER
      orientation = LinearLayout.VERTICAL
      addView(
        TextView(context).apply {
          layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
          text = "DashBoard"
        })
      addView(
        Button(context).apply {
          layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
          text = "To DashBoardChild"
          setOnClickListener {
            navigate( DashboardFragment2::class.java )
          }
        })
    }
  }
}

class DashboardFragment2 : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return LinearLayout(requireContext()).apply {
      layoutParams = ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
      gravity = Gravity.CENTER
      addView(
        TextView(context).apply {
          layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
          text = "DashBoard2"
        })
    }
  }
}