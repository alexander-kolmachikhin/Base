package wsr.base.presentation.app.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wsr.base.R
import wsr.base.databinding.ProfileBinding
import wsr.base.domain.AuthController
import wsr.base.domain.TestApi
import wsr.base.domain.TestResponse

class ProfileFragment : Fragment(R.layout.profile) {

    private val binding: ProfileBinding by viewBinding()
    private val authController: AuthController by inject()
    private val testApi: TestApi by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logOutButton.setOnClickListener {
            authController.setAuthorized(false)
            setFragmentResult("logOut", bundleOf())
        }

        testVolley()
        testRetrofit()
    }

    private fun testVolley() {
        val queue = Volley.newRequestQueue(requireContext())
        queue.add(StringRequest(Request.Method.GET, "http://nhtk-edu.ru/ru/students/full-time/schedule/item/329-09-05-17", {
            binding.textView.text = it
        }, {
            binding.textView.text = it.localizedMessage
        }))
    }

    private fun testRetrofit() {
        testApi.getLatest().enqueue(object : Callback<TestResponse> {
            override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                binding.textView2.text = response.body().toString()
            }

            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                binding.textView2.text = t.localizedMessage
            }
        })
    }
}