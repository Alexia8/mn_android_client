package com.alexiacdura.mn_core.data.user

import com.alexiacdura.mn_core.CommonMockUtilsUser
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import com.alexiacdura.mn_core.data.network.user.UserApiImpl
import com.alexiacdura.mn_core.data.network.user.UserApiService
import com.alexiacdura.mn_core.data.user.states.UserFeedState
import com.alexiacdura.mn_core.data.user.states.UserPostsState
import com.alexiacdura.mn_core.data.user.states.UserStarredState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class UserFeedInteractorImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var interactor: UserFeedInteractorImpl

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

        interactor = UserFeedInteractorImpl(mockApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test success state user feed`() {
        val testObserver = createTestObserver(
            false,
            CommonMockUtilsUser.USER_FEED,
            interactor.getUserRecentFeedPosts(USER, LIMFROM, LIMTO)
        )
        assertSuccessStateUserFeed(testObserver)
    }

    @Test
    fun `test error state user feed`() {
        val testObserver = createTestObserver(
            true, CommonMockUtilsUser.USER_FEED, interactor.getUserRecentFeedPosts(
                USER, LIMFROM,
                LIMTO
            )
        )
        assertErrorStateUserFeed(testObserver)
    }

    @Test
    fun `test success state user posts`() {
        val testObserver = createTestObserver(
            false,
            CommonMockUtilsUser.USER_POSTS,
            interactor.getUserRecentPosts(USER, LIMFROM, LIMTO)
        )
        assertSuccessStateUserPosts(testObserver)
    }

    @Test
    fun `test error state user posts`() {
        val testObserver = createTestObserver(
            true, CommonMockUtilsUser.USER_POSTS, interactor.getUserRecentPosts(
                USER, LIMFROM,
                LIMTO
            )
        )
        assertErrorStateUserPosts(testObserver)
    }

    @Test
    fun `test success state user starred`() {
        val testObserver = createTestObserver(
            false,
            CommonMockUtilsUser.USER_STARRED,
            interactor.getUserRecentStarredPosts(USER, LIMFROM, LIMTO)
        )
        assertSuccessStateUserStarred(testObserver)
    }

    @Test
    fun `test error state user starred`() {
        val testObserver = createTestObserver(
            true, CommonMockUtilsUser.USER_STARRED, interactor.getUserRecentStarredPosts(
                USER, LIMFROM,
                LIMTO
            )
        )
        assertErrorStateUserStarred(testObserver)
    }

    private fun assertSuccessStateUserFeed(testObserver: TestObserver<UserFeedState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserFeedState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserFeedState.Success)
    }

    private fun assertErrorStateUserFeed(testObserver: TestObserver<UserFeedState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserFeedState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserFeedState.Error)
    }

    private fun assertSuccessStateUserPosts(testObserver: TestObserver<UserPostsState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserPostsState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserPostsState.Success)
    }

    private fun assertErrorStateUserPosts(testObserver: TestObserver<UserPostsState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserPostsState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserPostsState.Error)
    }

    private fun assertSuccessStateUserStarred(testObserver: TestObserver<UserStarredState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserStarredState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserStarredState.Success)
    }

    private fun assertErrorStateUserStarred(testObserver: TestObserver<UserStarredState>) {
        Assert.assertEquals(2, testObserver.valueCount())
        Assert.assertTrue(testObserver.values()[0] is UserStarredState.Loading)
        Assert.assertTrue(testObserver.values()[1] is UserStarredState.Error)
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
        const val USER = 1
        const val LIMTO = 20
        const val LIMFROM = 0
    }
}