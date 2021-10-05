package com.techsensei.data.di

import com.techsensei.domain.repository.AuthRepository
import com.techsensei.domain.use_case.auth.RegisterUserUseCase
import com.techsensei.domain.use_case.auth.VerifyUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideVerifyUserUseCase(authRepository: AuthRepository): VerifyUserUseCase {
        return VerifyUserUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRegisterUserUseCase(authRepository: AuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(authRepository)
    }

}
