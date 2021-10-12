package com.techsensei.data.di

import com.techsensei.data.BuildConfig
import com.techsensei.data.network.AuthApiClient
import com.techsensei.data.network.ChatClient
import com.techsensei.data.repository.AuthRepositoryImpl
import com.techsensei.data.repository.ChatRepositoryImpl
import com.techsensei.domain.repository.AuthRepository
import com.techsensei.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.APP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthApiClient(retrofit: Retrofit): AuthApiClient {
        return retrofit.create(AuthApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideChatClient(retrofit: Retrofit): ChatClient {
        return retrofit.create(ChatClient::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authApiClient: AuthApiClient): AuthRepository =
        AuthRepositoryImpl(authApiClient)

    @Singleton
    @Provides
    fun provideChatRepository(chatClient: ChatClient): ChatRepository =
        ChatRepositoryImpl(chatClient)

}