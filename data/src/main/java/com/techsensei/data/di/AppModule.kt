package com.techsensei.data.di

import android.content.Context
import com.techsensei.data.BuildConfig
import com.techsensei.data.network.*
import com.techsensei.data.repository.*
import com.techsensei.data.utils.PrefsProvider
import com.techsensei.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .baseUrl(BuildConfig.APP_BASE_URL)//TODO change server base url value to your server url in "gradle.properties" file
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    //    Client
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
    fun providePrefsProvider(@ApplicationContext context: Context): PrefsProvider {
        return PrefsProvider(context)
    }

    @Singleton
    @Provides
    fun provideNotificationsClient(retrofit: Retrofit): NotificationsClient {
        return retrofit.create(NotificationsClient::class.java)
    }

    @Singleton
    @Provides
    fun provideUsersClient(retrofit: Retrofit): UsersClient {
        return retrofit.create(UsersClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileClient(retrofit: Retrofit): ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }

//    Clients end

//    Repos

    @Singleton
    @Provides
    fun provideAuthRepository(authApiClient: AuthApiClient): AuthRepository =
        AuthRepositoryImpl(authApiClient)

    @Singleton
    @Provides
    fun provideChatRepository(chatClient: ChatClient): ChatRepository =
        ChatRepositoryImpl(chatClient)

    @Singleton
    @Provides
    fun provideUsersRepository(usersClient: UsersClient): UsersRepository =
        UserRepositoryImpl(usersClient)

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileClient: ProfileClient,
        @ApplicationContext context: Context
    ): ProfileRepository =
        ProfileRepositoryImpl(profileClient, context)


    @Singleton
    @Provides
    fun provideNotificationsRepository(notificationsClient: NotificationsClient): NotificationsRepository =
        NotificationsRepositoryImpl(notificationsClient)

}