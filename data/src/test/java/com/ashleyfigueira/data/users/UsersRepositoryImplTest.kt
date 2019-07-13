package com.ashleyfigueira.data.users

import org.junit.After
import org.junit.Before

import androidx.room.EmptyResultSetException
import com.ashleyfigueira.data.common.ErrorMapper
import com.ashleyfigueira.data.common.OrderEntityMapper
import com.ashleyfigueira.data.users.local.UserRoomEntity
import com.ashleyfigueira.data.users.local.UsersDao
import com.ashleyfigueira.data.users.remote.UsersResponse
import com.ashleyfigueira.data.users.remote.UsersService
import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.entities.UserEntity
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class UsersRepositoryImplTest {

    private lateinit var usersRepository: UsersRepositoryImpl
    private val usersDao: UsersDao = mock()
    private val usersService: UsersService = mock()
    private val usersEntityMapper: UsersEntityMapper = UsersEntityMapper()
    private val orderEntityMapper: OrderEntityMapper = OrderEntityMapper()
    private val errorMapper: ErrorMapper = ErrorMapper()

    @Before
    fun setUp() {
        whenever(usersDao.insertUsers(anyList())).thenReturn(Completable.complete())
        whenever(usersDao.getUsers()).thenReturn(Single.just(roomUsers()))
        whenever(usersService.getUsers(anyInt(), anyString(), anyString(), anyString())).thenReturn(Single.just(usersResponse()))

        usersRepository = UsersRepositoryImpl(usersDao, usersService, errorMapper, usersEntityMapper, orderEntityMapper)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(usersDao, usersService)
    }

    @Test
    fun testGivenWeHaveUsersInDb_thenWeDoNotGoToNetwork() {
        usersRepository.getUsers(20, OrderEntity.Descending).test()
            .assertNoErrors()
            .assertValueCount(1) //Only database values where emitted
            .assertValue { it is StackResult.Success }

        verify(usersDao).getUsers()
        verify(usersService).getUsers(anyInt(), anyString(), anyString(), anyString())
        verify(usersDao, never()).insertUsers(anyList()) //This is proof that it didnt go to network
    }

    @Test
    fun testGivenWeHaveNoUsersInDb_thenReturnFromNetwork_andSaveToDb() {
        whenever(usersDao.getUsers()).thenReturn(Single.just(emptyList()))

        usersRepository.getUsers(20, OrderEntity.Descending).test()
            .assertNoErrors()
            .assertValueCount(1) //Only network values where emitted
            .assertValue { it is StackResult.Success }

        verify(usersDao).getUsers()
        verify(usersService).getUsers(anyInt(), anyString(), anyString(), anyString())
        verify(usersDao).insertUsers(anyList())
    }

    @Test
    fun testGivenWeAreOfflineAndHaveNoUsersInDb_thenReturnAnOfflineError() {
        whenever(usersDao.getUsers()).thenReturn(Single.error(EmptyResultSetException("No events in database!")))
        whenever(usersService.getUsers(anyInt(), anyString(), anyString(), anyString())).thenReturn(Single.error(UnknownHostException()))


        usersRepository.getUsers(20, OrderEntity.Descending).test()
            .assertNoErrors()
            .assertValueCount(1) //Only network values where emitted
            .assertValue { it is StackResult.Failure && it.error is StackError.Offline }

        verify(usersDao).getUsers()
        verify(usersService).getUsers(anyInt(), anyString(), anyString(), anyString())
        verify(usersDao, never()).insertUsers(anyList())

    }

    @Test
    fun testGivenIFetchUserByIdThenReturnUserFromDb() {
        whenever(usersDao.getUser(anyLong())).thenReturn(Single.just(roomUsers().first()))

        usersRepository.getUser(1L).test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it is StackResult.Success }

        verify(usersDao).getUser(anyLong())
    }

    @Test
    fun testGivenIFetchUserByIdAndNoUserIsInDb_thenThrowError() {
        whenever(usersDao.getUser(anyLong())).thenReturn(Single.error(EmptyResultSetException("")))

        usersRepository.getUser(1L).test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it is StackResult.Failure }

        verify(usersDao).getUser(anyLong())
    }

    companion object {
        fun users() = listOf(UserEntity(1, "name", 1234, "url", "location", DateTime.now(), false, false))

        fun roomUsers() = listOf(UserRoomEntity(1,"name", 1234, "url", "location", DateTime.now().millis))

        fun usersResponse() = UsersResponse(true, null, 1,1)
    }
}