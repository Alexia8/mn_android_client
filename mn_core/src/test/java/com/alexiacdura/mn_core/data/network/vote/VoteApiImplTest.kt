package com.alexiacdura.mn_core.data.network.vote

import com.alexiacdura.mn_core.CommonMockUtilsVote
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import com.alexiacdura.mn_core.getTestVoteResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class VoteApiImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockApi: VoteApiImpl

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
            VoteApiService::class.java,
            mockUrl
        )

        mockApi = VoteApiImpl(serviceFactory)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * Test error with a malformed response.*/
    //Create Vote end point
    @Test
    fun `create vote with malformed response`() {
        val response = CommonMockUtilsVote.getResponse(true, CommonMockUtilsVote.VOTE_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.createVote(vote).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    //Delete Vote end point
    @Test
    fun `delete vote with malformed response`() {
        val response = CommonMockUtilsVote.getResponse(true, CommonMockUtilsVote.VOTE_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.deleteVote(VOTEID).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    /**
     * Test vote response from the mockApi.*/
    @Test
    fun `get network response create vote`() {
        val testVoteResponse = getTestVoteResponse()

        val response = CommonMockUtilsVote.getResponse(false, CommonMockUtilsVote.VOTE_RESPONSE)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.createVote(vote).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseVote = testObserver.values()[0]

        CommonMockUtilsVote.assertVoteResponseEquals(testVoteResponse, responseVote)
    }

    @Test
    fun `get network response delete vote`() {
        val testVoteResponse = getTestVoteResponse()

        val response = CommonMockUtilsVote.getResponse(false, CommonMockUtilsVote.VOTE_RESPONSE)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.deleteVote(VOTEID).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseVote = testObserver.values()[0]

        CommonMockUtilsVote.assertVoteResponseEquals(testVoteResponse, responseVote)
    }

    /**
     * Test vote response size.*/
    @Test
    fun `get expected size of create vote response`() {
        val response = CommonMockUtilsVote.getModifiedResponse("title", CommonMockUtilsVote.VOTE_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.createVote(vote).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(0, testObserver.values().size)
    }

    @Test
    fun `get expected size of delete vote response`() {
        val response = CommonMockUtilsVote.getModifiedResponse("title", CommonMockUtilsVote.VOTE_RESPONSE)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.deleteVote(VOTEID).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(0, testObserver.values().size)
    }

    companion object {
        val vote = Vote(false, 1, 1)
        const val VOTEID = 10
    }
}