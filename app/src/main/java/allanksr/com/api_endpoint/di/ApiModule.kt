package allanksr.com.api_endpoint.di

import allanksr.com.api_endpoint.DefaultDispatchers
import allanksr.com.api_endpoint.DispatcherProvider
import allanksr.com.api_endpoint.common.Constants.dataBaseName
import allanksr.com.api_endpoint.common.Constants.endPointUrl
import allanksr.com.api_endpoint.data.remote.IEndPointApi
import allanksr.com.api_endpoint.data.remote.local.PromotionalDao
import allanksr.com.api_endpoint.data.remote.local.PromotionalItemDatabase
import allanksr.com.api_endpoint.repositories.DefaultPromotionalRepository
import allanksr.com.api_endpoint.repositories.PromotionalRepository
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PromotionalItemDatabase::class.java, dataBaseName).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: PromotionalDao,
        api: IEndPointApi
    ) = DefaultPromotionalRepository(dao, api) as PromotionalRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: PromotionalItemDatabase
    ) = database.promotionalDao()

    @Provides
    @Singleton
    fun endPointApi(): IEndPointApi {
        return Retrofit.Builder()
            .baseUrl(endPointUrl)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider {
        return DefaultDispatchers()
    }

}