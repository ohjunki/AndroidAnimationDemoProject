package ojk.animation.portfolio.fragmentmanaging.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import kotlinx.android.synthetic.main.fragment_stackable.view.*
import ojk.animation.portfolio.R
import ojk.animation.portfolio.navigationhelper.BackPressControlableFragment
import ojk.animation.portfolio.navigationhelper.SimpleNavigate

class StackableFragment( val firstClass : Class<*> ) : Fragment(), SimpleNavigate,
    BackPressControlableFragment {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate( R.layout.fragment_stackable , container, false )
    }

    override fun onResume() {
        super.onResume()
        navigate(firstClass, null)
    }

    override fun navigate( fragment : Class<*>, shareElement : List<View>?) {
        val fragment : Fragment = childFragmentManager.fragmentFactory.instantiate(
            fragment.classLoader!!,
            fragment.name
        )

        val beginTransaction = childFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fragment_container, fragment )
        beginTransaction.addToBackStack(null);
        shareElement?.forEach{ v-> beginTransaction.addSharedElement(v, v.transitionName)}
        beginTransaction.commit()
    }

    override fun onBackPressed() : Boolean {
        if( childFragmentManager.backStackEntryCount > 1 ){
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }

    private val map = HashMap<String,Any>();
    override fun getBundle(): MutableMap<String, Any> = map
}

