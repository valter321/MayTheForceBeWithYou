package com.valter.maytheforcebewith_valterfrancisco.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.valter.maytheforcebewith_valterfrancisco.BuildConfig
import com.valter.maytheforcebewith_valterfrancisco.data.db.SwapiDatabase
import com.valter.maytheforcebewith_valterfrancisco.data.dispatchers.AppDispatchersContainer
import com.valter.maytheforcebewith_valterfrancisco.data.dispatchers.DispatchersContainer
import com.valter.maytheforcebewith_valterfrancisco.data.network.ConnectivityInterceptor
import com.valter.maytheforcebewith_valterfrancisco.data.network.ConnectivityInterceptorImpl
import com.valter.maytheforcebewith_valterfrancisco.data.network.SwapiService
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepository
import com.valter.maytheforcebewith_valterfrancisco.data.repository.SwapiRepositoryImpl
import com.valter.maytheforcebewith_valterfrancisco.ui.list.MainViewModel
import com.valter.maytheforcebewith_valterfrancisco.ui.list.PersonListNavigation
import com.valter.maytheforcebewith_valterfrancisco.ui.list.PersonListNavigationImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
@FlowPreview
object DataModule {
    val module = module {
        single { provideMoshi() }
        single { provideLoggingInterceptor() }
        single<ConnectivityInterceptor> { provideConnectivityInterceptor(get()) }
        single { provideDefaultOkHttpClient(get(), get()) }
        single { provideRetrofit(get(), get()) }
        single { provideDatabase(androidApplication()) }
        single { providePersonDao(get()) }
        single { provideSwapiService(get()) }
        single<DispatchersContainer> { AppDispatchersContainer() }
        single<SwapiRepository> { SwapiRepositoryImpl(get(), get(), get()) }
        viewModel { MainViewModel(get(), get()) }
        factory<PersonListNavigation> { (fragment: Fragment) -> PersonListNavigationImpl(fragment.findNavController()) }
    }
}

fun provideMoshi(): Moshi = Moshi.Builder().build()

fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun provideConnectivityInterceptor(context: Context) = ConnectivityInterceptorImpl(context)

fun provideDefaultOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                               connectivityInterceptor: ConnectivityInterceptor
) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(connectivityInterceptor)
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