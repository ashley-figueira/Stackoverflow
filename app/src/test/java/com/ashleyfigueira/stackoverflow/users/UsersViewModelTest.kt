package com.ashleyfigueira.stackoverflow.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.ashleyfigueira.data.users.local.UserRoomEntity
import com.ashleyfigueira.data.users.remote.UsersResponse
import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.usecases.GetUsersUseCase
import com.ashleyfigueira.stackoverflow.base.ScreenState
import com.jraska.livedata.test
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersViewModelTest {

    private val getUsersUseCase: GetUsersUseCase = mock()
    private lateinit var usersViewModel: UsersViewModel
    private val lifecycleOwner: LifecycleOwner = mock()

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        usersViewModel = UsersViewModel(getUsersUseCase)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(getUsersUseCase)
    }

    @Test
    fun testGivenILandOnScreen_thenUsersAreLoaded() {
        whenever(getUsersUseCase(anyInt(), any())).thenReturn(Flowable.just(StackResult.Success(users())))

        val testObserver = usersViewModel.screenState.test()

        usersViewModel.onCreate(lifecycleOwner)

        testObserver
            .assertHasValue()
            .assertHistorySize(3)
            .assertValue { it is ScreenState.Success }
            .assertNever { it is ScreenState.NoInternet }
            .assertNever { it is ScreenState.Empty }
            .assertNever { it is ScreenState.Error }

        verify(getUsersUseCase).invoke(anyInt(), any())
    }

    @Test
    fun testGivenILandOnScreenAndIHaveNoInternet_thenShowInternetState() {
        whenever(getUsersUseCase(anyInt(), any())).thenReturn(Flowable.just(StackResult.Failure(StackError.Offline(Exception()))))

        val testObserver = usersViewModel.screenState.test()

        usersViewModel.onCreate(lifecycleOwner)

        testObserver
            .assertHasValue()
            .assertHistorySize(3)
            .assertValue { it is ScreenState.NoInternet }
            .assertNever { it is ScreenState.Success }
            .assertNever { it is ScreenState.Empty }
            .assertNever { it is ScreenState.Error }

        verify(getUsersUseCase).invoke(anyInt(), any())
    }

    @Test
    fun testGivenThereAreNoUsers_thenShowEmptyState() {
        whenever(getUsersUseCase(anyInt(), any())).thenReturn(Flowable.just(StackResult.Success(emptyList())))

        val testObserver = usersViewModel.screenState.test()

        usersViewModel.onCreate(lifecycleOwner)

        testObserver
            .assertHasValue()
            .assertHistorySize(3)
            .assertValue { it is ScreenState.Empty }
            .assertNever { it is ScreenState.Success }
            .assertNever { it is ScreenState.NoInternet }
            .assertNever { it is ScreenState.Error }

        verify(getUsersUseCase).invoke(anyInt(), any())
    }


    companion object {
        fun users() = listOf(UserEntity(1, "name", 1234, "url", "location", DateTime.now(), false, false))
    }

}