package ojk.animation.portfolio.utils.navigation

import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions

interface FragmentNavigation{
    val mFragNavController : FragNavController
    var popFragNavTransactionOptions : FragNavTransactionOptions?
}