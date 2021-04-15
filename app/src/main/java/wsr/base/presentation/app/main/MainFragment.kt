package wsr.base.presentation.app.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import wsr.base.R
import wsr.base.databinding.MainBinding
import wsr.base.presentation.app.ads.AdsFragment
import wsr.base.presentation.app.films.FilmsFragment
import wsr.base.presentation.app.location.LocationFragment
import wsr.base.presentation.app.profile.ProfileFragment
import wsr.base.presentation.ext.showFragment

class MainFragment : Fragment(R.layout.main) {

    private val binding: MainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.films -> showFragment<FilmsFragment>()
                R.id.ads -> showFragment<AdsFragment>()
                R.id.profile -> showFragment<ProfileFragment>()
                R.id.location -> showFragment<LocationFragment>()
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