package wsr.base.presentation.app.launch

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import wsr.base.R
import wsr.base.databinding.LaunchBinding

class LaunchFragment : Fragment(R.layout.launch) {

    private val viewModel: LaunchViewModel by viewModel()
    private val binding: LaunchBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(view)
            .load("https://filmdar.com/tms-loading.gif")
            .into(binding.loadingImageView)

        viewModel.checkAuth()
        viewModel.liveResult.observe(viewLifecycleOwner) {
            when (it) {
                LaunchViewModel.Result.GoToAuth -> setFragmentResult("goToAuth", bundleOf())
                LaunchViewModel.Result.GoToMain -> setFragmentResult("goToMain", bundleOf())
            }
        }
    }
}