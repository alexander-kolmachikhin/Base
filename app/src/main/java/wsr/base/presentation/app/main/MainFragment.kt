package wsr.base.presentation.app.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import wsr.base.R
import wsr.base.databinding.MainBinding
import wsr.base.presentation.app.currencies.CurrenciesFragment
import wsr.base.presentation.app.profile.ProfileFragment
import wsr.base.presentation.ext.showFragment

class MainFragment : Fragment(R.layout.main) {

    private val binding: MainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.currencies -> showFragment<CurrenciesFragment>(R.id.fragmentContainerView)
                R.id.profile -> showFragment<ProfileFragment>(R.id.fragmentContainerView)
            }
            true
        }

        childFragmentManager.setFragmentResultListener("logOut", viewLifecycleOwner) { key, bundle ->
            setFragmentResult("goToAuth", bundleOf())
        }
    }
}