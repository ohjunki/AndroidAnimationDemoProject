package ojk.animation.portfolio.fragmentmanaging.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ojk.animation.portfolio.navigationhelper.pushFragment

class CodeViewFragment : Fragment() {
  private var mInt : Int = 0

  companion object{
      const val FRAG_INDEX = "FRAG_INDEX"
      fun newInstance(index: Int): CodeViewFragment {
          val args = Bundle().apply { putInt(FRAG_INDEX, index) }
          val fragment =
              CodeViewFragment()
          fragment.arguments = args
          return fragment
      }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      mInt = requireArguments()[FRAG_INDEX] as Int
  }

  /**
   * TODO : 코드로 짜는 경우에는 View State가 유지되지 않는다...
   */
  lateinit var rootView : View
  lateinit var editText : View
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      rootView = LinearLayout(requireContext()).apply {
          layoutParams = ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL
            addView(
                TextView(context).apply {
                  layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                  text = "Code Base View $mInt"
                })
            addView(
                EditText(context).apply {
                  layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                  minWidth = 500
                  hint = "Write something"
                }.apply { editText = this })
            addView(
                Button(context).apply {
                  layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                  text = "To DashBoard ${mInt+1}"
                  setOnClickListener {
                    pushFragment(
                        newInstance(
                            mInt + 1
                        ), null)
                  }
                })
        }
      return rootView
  }
}