package com.alexiacdura.mn_core.data.vote

import com.alexiacdura.mn_core.CommonMockUtilsVote
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import com.alexiacdura.mn_core.data.network.entities.insert.Vote
import com.alexiacdura.mn_core.data.network.vote.VoteApiImpl
import com.alexiacdura.mn_core.data.network.vote.VoteApiService
import com.alexiacdura.mn_core.data.star.states.DeleteVoteState
import com.alexiacdura.mn_core.data.user.DeleteVoteInteractorImpl
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class DeleteVoteInteractorImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var interactor: DeleteVoteInteractorImpl

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

        val mockApi = VoteApiImpl(serviceFactory)

        interactor = DeleteVoteInteractorImpl(mockApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test success state delete vote`() {
        val testObserver = createTestObserver(
            false,
            CommonMockUtilsVote.VOTE_RESPONSE,
            interactor.deleteVote(vote)
        )
        assertSuccessState(testObserver)
    }

    @Test
    fun `test error state delete vote`() {
        val testObserver = createTestObserver(
            true,
            CommonMockUtilsVote.VOTE_RESPONSE,
            interactor.deleteVote(vote)
        )
        assertErrorState(testObserver)
    }

    private fun assertSuccessState(testObserver: TestObserver<DeleteVoteState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is DeleteVoteState.Loading)
        Assert.assertTrue(testObserver.values()[1] is DeleteVoteState.Success)
    }

    private fun assertErrorState(testObserver: TestObserver<DeleteVoteState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is DeleteVoteState.Loading)
        Assert.assertTrue(testObserver.values()[1] is DeleteVoteState.Error)
    }

    private fun <T> createTestObserver(
        malformed: Boolean,
        mockFile: String,
        observable: Observable<T>
    ): TestObserver<T> {
        val response = CommonMockUtilsVote.getResponse(malformed, mockFile)
        mockWebServer.enqueue(response)

        val testObserver = observable.test()

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        return testObserver
    }

    companion object {
        val vote = 4
    }
}