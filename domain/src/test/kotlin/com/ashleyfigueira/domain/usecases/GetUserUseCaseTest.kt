package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.common.TestSchedulerProvider
import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.repositories.UsersRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun setUp() {
        getUserUseCase = GetUserUseCase(usersRepository, TestSchedulerProvider())
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(usersRepository)
    }

    @Test
    fun givenIFetchUsersThenSuccess() {
        whenever(usersRepository.getUser(anyLong())).thenReturn(Single.just(StackResult.Success(UserEntity(
            1,
            "name",
            1234,
            "url",
            "location",
            DateTime.now(),
            false,
            false
        ))))

        getUserUseCase(1L).test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it is StackResult.Success }

        verify(usersRepository).getUser(anyLong())
    }

    @Test
    fun givenIFetchUsersThenFailure() {
        whenever(usersRepository.getUser(anyLong())).thenReturn(Single.just(StackResult.Failure(StackError.NothingInDatabase(Exception()))))

        getUserUseCase(1L).test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it is StackResult.Failure }

        verify(usersRepository).getUser(anyLong())

    }
}