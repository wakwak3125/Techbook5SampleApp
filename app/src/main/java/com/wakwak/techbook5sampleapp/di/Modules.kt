package com.wakwak.techbook5sampleapp.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wakwak.techbook5sampleapp.api.GitHubService
import com.wakwak.techbook5sampleapp.model.data.AppDatabase
import com.wakwak.techbook5sampleapp.model.repositories.GitHubUserRepository
import com.wakwak.techbook5sampleapp.model.repositories.IGitHubUserRepository
import com.wakwak.techbook5sampleapp.model.usecases.GitHubUserPageUseCase
import com.wakwak.techbook5sampleapp.model.usecases.IGitHubUserPageUseCase
import com.wakwak.techbook5sampleapp.presentation.presenters.GitHubUserPresenter
import com.wakwak.techbook5sampleapp.utils.AndroidWrapper
import com.wakwak.techbook5sampleapp.utils.IAndroidWrapper
import com.wakwak.techbook5sampleapp.utils.PreferencesUtil
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class Modules(private val context: Context,
              private val db: AppDatabase) {

    val appModule = module {

        single {
            Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .client(OkHttpClient.Builder().addInterceptor(createInterceptor(get())).build())
                    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
                    .create(GitHubService::class.java)
        }

        single { db.gitHubUserStore() }

        single { GitHubUserRepository(get(), get()) as IGitHubUserRepository }

        single { PreferencesUtil(context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)) }

        module("gitHubUser") {
            factory { AndroidWrapper(context) as IAndroidWrapper }
            factory { GitHubUserPageUseCase(get()) as IGitHubUserPageUseCase }
            factory { GitHubUserPresenter(get(), get()) }
        }
    }

    private fun createInterceptor(prefs: PreferencesUtil): Interceptor {
        return Interceptor {
            val org = it.request()
            it.proceed(
                    org.newBuilder()
                            .addHeader("Accept", "application/vnd.github.v3+json")
                            /*.addHeader("Authorization", "token ${prefs.getToken()}")*/
                            .build())
        }
    }
}
