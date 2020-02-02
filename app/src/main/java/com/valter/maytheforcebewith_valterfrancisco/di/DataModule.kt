package com.valter.maytheforcebewith_valterfrancisco.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.valter.maytheforcebewith_valterfrancisco.BuildConfig
import com.valter.maytheforcebewith_valterfrancisco.data.db.SwapiDatabase
import com.valter.maytheforcebewith_valterfrancisco.data.network.SwapiService
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepository
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepositoryImpl
import com.valter.maytheforcebewith_valterfrancisco.ui.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DataModule {
    val module = module {
        single { provideMoshi() }
        single { provideDefaultOkHttpClient() }
        single { provideRetrofit(get(), get()) }
        single { provideDatabase(androidApplication()) }
        single { providePersonDao(get()) }
        single { provideSwapiService(get()) }
        single<SwapiRepository> { SwapiRepositoryImpl(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

fun provideMoshi(): Moshi = Moshi.Builder().build()

fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun provideDefaultOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .build()

fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        SwapiDatabase::class.java,
        "swapi.db"
).fallbackToDestructiveMigration()
        .build()

fun providePersonDao(database: SwapiDatabase) = database.personDao()

fun provideSwapiService(retrofit: Retrofit): SwapiService = retrofit.create(SwapiService::class.java)