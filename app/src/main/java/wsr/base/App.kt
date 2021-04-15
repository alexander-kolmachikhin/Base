package wsr.base

import android.app.Application
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import wsr.base.domain.AuthController
import wsr.base.domain.TestApi
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
        single {
            Retrofit.Builder().apply {
                addConverterFactory(ScalarsConverterFactory.create())
                addConverterFactory(GsonConverterFactory.create())

                client(
                    OkHttpClient.Builder().apply {
                        addInterceptor(
                            HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)
                        )

                        addInterceptor {
                            it.proceed(
                                it.request().newBuilder()
                                    .header("Authorization", "123")
                                    .build()
                            )
                        }
                    }.build()
                )

                baseUrl("http://192.168.1.186:8080")
            }.build()
        }
        single { get<Retrofit>().create(TestApi::class.java) }
    }
}