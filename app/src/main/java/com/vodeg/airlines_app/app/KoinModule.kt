package com.vodeg.airlines_app.app

import androidx.room.Room
import com.vodeg.airlines_app.data.db.AirlineDataBase
import com.vodeg.airlines_app.data.repository.AirlineRepoImp
import com.vodeg.airlines_app.domain.repository.AirlinesRepo
import com.vodeg.airlines_app.domain.usecase.AddNewAirline
import com.vodeg.airlines_app.domain.usecase.Filter
import com.vodeg.airlines_app.domain.usecase.GetAllAirlines
import com.vodeg.airlines_app.presentation.home.HomeViewModel
import com.vodeg.airlines_app.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AirlineDataBase::class.java,
            "database.db"
        ).build()
    }
    single { get<AirlineDataBase>().getAirlineDao() }

    single { provideRetrofit() }

    single<AirlinesRepo> { AirlineRepoImp(get(), get()) }

    single { GetAllAirlines(get()) }
    single { AddNewAirline(get()) }
    single { Filter(get()) }

    viewModel { HomeViewModel(get(), get(), get()) }
}

fun provideRetrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    return Retrofit.Builder().baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
}