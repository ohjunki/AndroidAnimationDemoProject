package ojk.animation.portfolio

import android.app.Application
import ojk.android.utils.AndroidUtilLibrary

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidUtilLibrary.initLibraryBuildConfig(this);
    }
}