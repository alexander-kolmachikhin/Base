package wsr.base.presentation.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.MobileAds
import wsr.base.R
import wsr.base.databinding.AppBinding
import wsr.base.presentation.app.auth.AuthFragment
import wsr.base.presentation.app.launch.LaunchFragment
import wsr.base.presentation.app.main.MainFragment
import wsr.base.presentation.ext.getFragment
import wsr.base.presentation.ext.showFragment
import kotlin.reflect.KClass

class AppActivity : AppCompatActivity(R.layout.app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}

        if (savedInstanceState == null) {
            showFragment<LaunchFragment>(R.id.fragmentContainerView, addToBackStack = false)
        }

        supportFragmentManager.setFragmentResultListener("authorized", this) { key, bundle ->
            showFragment<MainFragment>(R.id.fragmentContainerView, addToBackStack = false)
        }

        supportFragmentManager.setFragmentResultListener("goToMain", this) { key, bundle ->
            showFragment<MainFragment>(R.id.fragmentContainerView, addToBackStack = false)
        }

        supportFragmentManager.setFragmentResultListener("goToAuth", this) { key, bundle ->
            showFragment<AuthFragment>(R.id.fragmentContainerView, addToBackStack = false)
        }
    }
}