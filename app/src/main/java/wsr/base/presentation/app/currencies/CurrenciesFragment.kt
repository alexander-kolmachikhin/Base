package wsr.base.presentation.app.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import wsr.base.R
import wsr.base.databinding.CurrenciesBinding

class CurrenciesFragment : Fragment(R.layout.currencies) {

    private val binding: CurrenciesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}