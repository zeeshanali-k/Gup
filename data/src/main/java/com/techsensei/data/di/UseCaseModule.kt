package com.techsensei.data.di

import com.techsensei.domain.repository.AuthRepository
import com.techsensei.domain.repository.ChatRepository
import com.techsensei.domain.repository.NotificationsRepository
import com.techsensei.domain.repository.UsersRepository
import com.techsensei.domain.use_case.auth.RegisterUserUseCase
import com.techsensei.domain.use_case.auth.VerifyUserUseCase
import com.techsensei.domain.use_case.chat.*
import com.techsensei.domain.use_case.notifications.GetNotifications
import com.techsensei.domain.use_case.users.GetUsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

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

    @Provides
    @ViewModelScoped
    fun provideGetAllChats(chatRepository: ChatRepository): GetAllChats {
        return GetAllChats(chatRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetChatMessages(chatRepository: ChatRepository): GetChatMessages {
        return GetChatMessages(chatRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSendMessage(chatRepository: ChatRepository): SendMessage {
        return SendMessage(chatRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUsers(usersRepository: UsersRepository): GetUsers {
        return GetUsers(usersRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideVerifyChat(chatRepository: ChatRepository): VerifyChat {
        return VerifyChat(chatRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRegisterChatEvent(chatRepository: ChatRepository): RegisterChatEvent {
        return RegisterChatEvent(chatRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNotifications(notificationsRepository: NotificationsRepository): GetNotifications {
        return GetNotifications(notificationsRepository)
    }

}
