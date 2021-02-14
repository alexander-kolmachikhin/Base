package wsr.base.presentation.app.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import wsr.base.R
import wsr.base.databinding.ProfileBinding
import wsr.base.domain.AuthController

class ProfileFragment : Fragment(R.layout.profile) {

    private val binding: ProfileBinding by viewBinding()
    private val authController: AuthController by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOutButton.setOnClickListener {
            authController.setAuthorized(false)
            setFragmentResult("logOut", bundleOf())
        }
    }
}