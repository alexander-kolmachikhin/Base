package wsr.base.presentation.app.auth

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import wsr.base.R
import wsr.base.databinding.AuthBinding
import wsr.base.domain.AuthController

class AuthFragment : Fragment(R.layout.auth) {

    private val binding: AuthBinding by viewBinding()
    private val authController: AuthController by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authButton.setOnClickListener {
            authController.setAuthorized(true)
            setFragmentResult("authorized", bundleOf())
        }
    }
}