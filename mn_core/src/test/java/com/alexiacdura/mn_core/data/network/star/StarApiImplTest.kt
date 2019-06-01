package com.alexiacdura.mn_core.data.network.star

import com.alexiacdura.mn_core.CommonMockUtilsStar
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import com.alexiacdura.mn_core.data.network.entities.insert.Star
import com.alexiacdura.mn_core.getTestStarResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class StarApiImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockApi: StarApiImpl

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

        mockApi = StarApiImpl(serviceFactory)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * Test error with a malformed response.*/
    //Create Star end point
    @Test
    fun `create star with malformed response`() {
        val response = CommonMockUtilsStar.getResponse(true, CommonMockUtilsStar.STAR_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.createStar(star).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    //Delete Star end point
    @Test
    fun `delete star with malformed response`() {
        val response = CommonMockUtilsStar.getResponse(true, CommonMockUtilsStar.STAR_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.deleteStar(STARID).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    /**
     * Test star response from the mockApi.*/
    @Test
    fun `get network response create star`() {
        val testStarResponse = getTestStarResponse()

        val response = CommonMockUtilsStar.getResponse(false, CommonMockUtilsStar.STAR_RESPONSE)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.createStar(star).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseStar = testObserver.values()[0]

        CommonMockUtilsStar.assertStarResponseEquals(testStarResponse, responseStar)
    }

    @Test
    fun `get network response delete star`() {
        val testStarResponse = getTestStarResponse()

        val response = CommonMockUtilsStar.getResponse(false, CommonMockUtilsStar.STAR_RESPONSE)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.deleteStar(STARID).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseStar = testObserver.values()[0]

        CommonMockUtilsStar.assertStarResponseEquals(testStarResponse, responseStar)
    }

    /**
     * Test star response size.*/
    @Test
    fun `get expected size of create star response`() {
        val response = CommonMockUtilsStar.getModifiedResponse("title", CommonMockUtilsStar.STAR_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.createStar(star).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(0, testObserver.values().size)
    }

    @Test
    fun `get expected size of delete star response`() {
        val response = CommonMockUtilsStar.getModifiedResponse("title", CommonMockUtilsStar.STAR_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.deleteStar(STARID).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(0, testObserver.values().size)
    }

    companion object {
        val star = Star(1, 1)
        const val STARID = 10
    }
}