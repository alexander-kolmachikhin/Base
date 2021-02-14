package wsr.base.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class AuthController(private val sharedPreferences: SharedPreferences) {

    fun setAuthorized(isAuthorized: Boolean) =
        sharedPreferences.edit { putBoolean("isAuthorized", isAuthorized) }

    suspend fun isAuthorized() = withContext(Dispatchers.IO) {
        delay(3000)
        sharedPreferences.getBoolean("isAuthorized", false)
    }
}