package com.ashleyfigueira.stackoverflow.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.usecases.BlockUserUseCase
import com.ashleyfigueira.domain.usecases.FollowUserUseCase
import com.ashleyfigueira.domain.usecases.GetUserUseCase
import com.ashleyfigueira.stackoverflow.base.ScreenState
import com.ashleyfigueira.stackoverflow.users.UsersViewModelTest.Companion.users
import com.jraska.livedata.test
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserDetailsViewModelTest {

    private val getUserUseCase: GetUserUseCase = mock()
    private val followUserUseCase: FollowUserUseCase = mock()
    private val blockUserUseCase: BlockUserUseCase = mock()
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userDetailsViewModel = UserDetailsViewModel(getUserUseCase, followUserUseCase, blockUserUseCase)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(getUserUseCase, followUserUseCase, blockUserUseCase)
    }

    @Test
    fun givenILoadUserThenSuccessState() {
        whenever(getUserUseCase(anyLong())).thenReturn(Flowable.just(StackResult.Success(users().first())))

        val testObserver= userDetailsViewModel.screenState.test()

        userDetailsViewModel.load(1L)

        testObserver
            .assertHasValue()
            .assertHistorySize(1)
            .assertValue { it is ScreenState.Success }
            .assertNever { it is ScreenState.NoInternet }
            .assertNever { it is ScreenState.Empty }

        verify(getUserUseCase).invoke(anyLong())

    }
}