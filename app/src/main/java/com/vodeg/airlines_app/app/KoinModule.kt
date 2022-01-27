package com.vodeg.airlines_app.app

import com.vodeg.airlines_app.data.repository.AirlineRepoImp
import com.vodeg.airlines_app.domain.repository.AirlinesRepo
import com.vodeg.airlines_app.domain.usecase.GetAllAirlines
import com.vodeg.airlines_app.presentation.home.HomeViewModel
import com.vodeg.airlines_app.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinModule = module {

    viewModel { HomeViewModel(get()) }
    single { provideRetrofit() }

    single<AirlinesRepo> { AirlineRepoImp(get()) }

    single { GetAllAirlines(get()) }
}

fun provideRetrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    return Retrofit.Builder().baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
}