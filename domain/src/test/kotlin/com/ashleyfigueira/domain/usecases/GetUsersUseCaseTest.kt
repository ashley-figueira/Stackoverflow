package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.common.TestSchedulerProvider
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.repositories.UsersRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun setUp() {
        getUsersUseCase = GetUsersUseCase(usersRepository, TestSchedulerProvider())
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(usersRepository)
    }

    @Test
    fun givenIFetchUsersThenSuccess() {
        whenever(usersRepository.getUsers(anyInt(), any())).thenReturn(Flowable.just(StackResult.Success(emptyList())))

        getUsersUseCase().test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it is StackResult.Success }

        verify(usersRepository).getUsers(anyInt(), any())
    }

    @Test
    fun givenIFetchUsersThenFailure() {
        whenever(usersRepository.getUsers(anyInt(), any())).thenReturn(Flowable.just(StackResult.Failure(StackError.Offline(Exception()))))

        getUsersUseCase().test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it is StackResult.Failure }

        verify(usersRepository).getUsers(anyInt(), any())

    }
}