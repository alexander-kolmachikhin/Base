package wsr.base.presentation.app.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wsr.base.domain.AuthController

class LaunchViewModel(private val authController: AuthController) : ViewModel() {

    val liveResult = MutableLiveData<Result>()

    fun checkAuth() = viewModelScope.launch {
        liveResult.value = when (authController.isAuthorized()) {
            true -> Result.GoToMain
            else -> Result.GoToAuth
        }
    }

    sealed class Result {
        object GoToMain : Result()
        object GoToAuth : Result()
    }
}