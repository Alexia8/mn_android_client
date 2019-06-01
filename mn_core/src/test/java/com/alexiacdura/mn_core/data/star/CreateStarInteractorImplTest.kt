package com.alexiacdura.mn_core.data.star

import com.alexiacdura.mn_core.CommonMockUtilsStar
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import com.alexiacdura.mn_core.data.network.entities.insert.Star
import com.alexiacdura.mn_core.data.network.star.StarApiImpl
import com.alexiacdura.mn_core.data.network.star.StarApiService
import com.alexiacdura.mn_core.data.star.states.CreateStarState
import com.alexiacdura.mn_core.data.user.CreateStarInteractorImpl
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class CreateStarInteractorImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var interactor: CreateStarInteractorImpl

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
            StarApiService::class.java,
            mockUrl
        )

        val mockApi = StarApiImpl(serviceFactory)

        interactor = CreateStarInteractorImpl(mockApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test success state create star`() {
        val testObserver = createTestObserver(
            false,
            CommonMockUtilsStar.STAR_RESPONSE,
            interactor.createStar(star)
        )
        assertSuccessState(testObserver)
    }

    @Test
    fun `test error state create star`() {
        val testObserver = createTestObserver(
            true,
            CommonMockUtilsStar.STAR_RESPONSE,
            interactor.createStar(star)
        )
        assertErrorState(testObserver)
    }

    private fun assertSuccessState(testObserver: TestObserver<CreateStarState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is CreateStarState.Loading)
        Assert.assertTrue(testObserver.values()[1] is CreateStarState.Success)
    }

    private fun assertErrorState(testObserver: TestObserver<CreateStarState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is CreateStarState.Loading)
        Assert.assertTrue(testObserver.values()[1] is CreateStarState.Error)
    }

    private fun <T> createTestObserver(
        malformed: Boolean,
        mockFile: String,
        observable: Observable<T>
    ): TestObserver<T> {
        val response = CommonMockUtilsStar.getResponse(malformed, mockFile)
        mockWebServer.enqueue(response)

        val testObserver = observable.test()

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        return testObserver
    }

    companion object {
        val star = Star(1, 1)
    }
}