package wsr.base.presentation.app.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import wsr.base.R
import wsr.base.databinding.MainBinding
import wsr.base.presentation.app.ads.AdsFragment
import wsr.base.presentation.app.films.FilmsFragment
import wsr.base.presentation.app.profile.ProfileFragment
import wsr.base.presentation.ext.showFragment

class MainFragment : Fragment(R.layout.main) {

    private val binding: MainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //childFragmentManager.registerFragmentLifecycleCallbacks(
        //    object : FragmentManager.FragmentLifecycleCallbacks() {
        //        override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
        //            if (fragment is FilmsFragment) {
        //                requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //            }
        //        }
//
        //        override fun onFragmentPaused(fm: FragmentManager, fragment: Fragment) {
        //            if (fragment is FilmsFragment) {
        //                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //            }
        //        }
        //    },
        //    false
        //)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.films -> showFragment<FilmsFragment>(R.id.fragmentContainerView)
                R.id.ads -> showFragment<AdsFragment>(R.id.fragmentContainerView)
                R.id.profile -> showFragment<ProfileFragment>(R.id.fragmentContainerView)
            }
            true
        }

        childFragmentManager.setFragmentResultListener(
            "logOut",
            viewLifecycleOwner
        ) { key, bundle ->
            setFragmentResult("goToAuth", bundleOf())
        }

        binding.bottomNavigationView.selectedItemId =
            savedInstanceState?.getInt("selectedItemId") ?: R.id.films
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", binding.bottomNavigationView.selectedItemId)
    }
}