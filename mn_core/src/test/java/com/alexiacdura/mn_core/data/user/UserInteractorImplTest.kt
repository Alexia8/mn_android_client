package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.CommonMockUtilsUser
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import com.alexiacdura.mn_core.data.network.user.UserApiImpl
import com.alexiacdura.mn_core.data.network.user.UserApiService
import com.alexiacdura.mn_core.data.user.states.UserState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class UserInteractorImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var interactor: UserInteractorImpl

    @Throws
    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val mockUrl = mockWebServer.url("").toString()

        MockitoAnnotations.initMocks(this)
        val serviceFactory = DefaultServiceFactory(
            MockSchedulerProvider(),
            DefaultOKHttpClientBuilder(),
            UserApiService::class.java,
            mockUrl
        )

        val mockApi = UserApiImpl(serviceFactory)

        interactor = UserInteractorImpl(mockApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test success state user`() {
        val testObserver = createTestObserver(
            false,
            CommonMockUtilsUser.USER,
            interactor.getUserByEmail(USER)
        )
        assertSuccessStateUser(testObserver)
    }

    @Test
    fun `test error state user`() {
        val testObserver = createTestObserver(
            true,
            CommonMockUtilsUser.USER,
            interactor.getUserByEmail(USER)
        )
        assertErrorStateUser(testObserver)
    }

    private fun assertSuccessStateUser(testObserver: TestObserver<UserState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserState.Success)
    }

    private fun assertErrorStateUser(testObserver: TestObserver<UserState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserState.Error)
    }

    private fun <T> createTestObserver(
        malformed: Boolean,
        mockFile: String,
        observable: Observable<T>
    ): TestObserver<T> {
        val response = CommonMockUtilsUser.getResponse(malformed, mockFile)
        mockWebServer.enqueue(response)

        val testObserver = observable.test()

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        return testObserver
    }

    companion object {
        const val USER = "mirceateodor.oprea@gmail.com"
    }
}