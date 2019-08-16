package com.religion76.mvpkotlin

import com.religion76.mvpkotlin.reddit.RedditApi
import com.religion76.mvpkotlin.ui.post.HotPostDataService
import com.religion76.mvpkotlin.ui.post.PostViewModel
import com.religion76.mvpkotlin.ui.search.SearchRedditViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-13.
 */
object AppModules {

    private const val BASE_URL = "https://www.reddit.com/"

    val appModule = module {
        single<OkHttpClient> {
            val builder = OkHttpClient.Builder()
            val logger = HttpLoggingInterceptor {
                Timber.d("OkHttp $it")
            }
            logger.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(logger)
            builder.build()
        }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single<RedditApi> {
            val retrofit: Retrofit = get()
            retrofit.create(RedditApi::class.java)
        }

        factory {
            HotPostDataService(get())
        }

        viewModel {
            PostViewModel()
        }

        viewModel {
            SearchRedditViewModel(get())
        }
    }
}