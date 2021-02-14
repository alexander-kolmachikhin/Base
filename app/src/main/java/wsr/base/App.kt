package wsr.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import wsr.base.domain.AuthController
import wsr.base.presentation.app.launch.LaunchViewModel

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                createDomainModule(),
                createPresentationModule()
            )
        }
    }

    private fun createPresentationModule() = module {
        viewModel { LaunchViewModel(get()) }
    }

    private fun createDomainModule() = module {
        single { AuthController(getSharedPreferences("AuthController", MODE_PRIVATE)) }
    }
}