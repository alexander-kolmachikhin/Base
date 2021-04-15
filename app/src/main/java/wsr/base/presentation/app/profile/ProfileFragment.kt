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
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wsr.base.R
import wsr.base.databinding.ProfileBinding
import wsr.base.domain.AuthController
import wsr.base.domain.TestApi
import wsr.base.domain.User

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
        testApi.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                binding.textView2.text = buildString {
                    response.body()?.forEach {
                        appendLine(it.name)
                    } ?: append("Empty")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                binding.textView2.text = t.localizedMessage
            }
        })

        testApi.get().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                binding.textView3.text = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                binding.textView3.text = t.localizedMessage
            }
        })
    }
}