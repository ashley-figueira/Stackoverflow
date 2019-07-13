package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.common.TestSchedulerProvider
import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.repositories.UsersRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FollowUserUseCaseTest {

    private lateinit var followUserUseCase: FollowUserUseCase
    private val usersRepository: UsersRepository = mock()

    @Before
    fun setUp() {
        followUserUseCase = FollowUserUseCase(usersRepository, TestSchedulerProvider())
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(usersRepository)
    }

    @Test
    fun testGivenIFollowUser_ThenUserIsChangedAndUpdated() {
        whenever(usersRepository.updateUser(any())).thenReturn(Completable.complete())
        whenever(usersRepository.getUser(anyLong())).thenReturn(Flowable.just(StackResult.Success(UserEntity(1, "name", 1234, "url", "location", DateTime.now(), false, false))))

        followUserUseCase(1L).test()
            .assertNoErrors()
            .assertComplete()

        val userCaptor = argumentCaptor<UserEntity>()
        verify(usersRepository).getUser(anyLong())
        verify(usersRepository).updateUser(userCaptor.capture())

        assertEquals(true, userCaptor.firstValue.isFollowing)
    }
}