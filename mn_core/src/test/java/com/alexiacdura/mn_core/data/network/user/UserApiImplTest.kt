package com.alexiacdura.mn_core.data.network.user

import com.alexiacdura.mn_core.CommonMockUtilsUser
import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.rx.MockSchedulerProvider
import getTestFeedPostUserFeed
import getTestFeedPostUserStarred
import getTestUser
import getTestUserData
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class UserApiImplTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockApi: UserApiImpl

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

        mockApi = UserApiImpl(serviceFactory)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * Test error with a malformed response.*/
    //UserData end point
    @Test
    fun `get user with malformed response`() {
        val response = CommonMockUtilsUser.getResponse(true, CommonMockUtilsUser.USER)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserByEmail(USEREMAIL).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    //UserFeed end point
    @Test
    fun `get starred feedPosts with malformed response`() {
        val response = CommonMockUtilsUser.getResponse(true, CommonMockUtilsUser.USER_STARRED)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserStarredPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    //UserStarredPosts end point
    @Test
    fun `get user recent feedPosts with malformed response`() {
        val response = CommonMockUtilsUser.getResponse(true, CommonMockUtilsUser.USER_POSTS)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserStarredPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    //UserRecentPosts end point
    @Test
    fun `get user feed with malformed response`() {
        val response = CommonMockUtilsUser.getResponse(true, CommonMockUtilsUser.USER_FEED)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserStarredPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    //UserData end point
    @Test
    fun `get user data with malformed response`() {
        val response = CommonMockUtilsUser.getResponse(true, CommonMockUtilsUser.USER_DATA)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserData(USER).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(1, testObserver.errorCount())
    }

    /**
     * Compare the manually constructed [FeedPost] with one from the mocked api response.*/
    //UserByEmail end point
    @Test
    fun `get user request network response`() {
        val testUser = getTestUser()

        val response = CommonMockUtilsUser.getResponse(false, CommonMockUtilsUser.USER)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.getUserByEmail(USEREMAIL).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseUser = testObserver.values()[0]

        CommonMockUtilsUser.assertUserEquals(testUser, responseUser)
    }

    //UserFeed end point
    @Test
    fun `get user request network response user feed`() {
        val testFeedPost = getTestFeedPostUserFeed()

        val response = CommonMockUtilsUser.getResponse(false, CommonMockUtilsUser.USER_FEED)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.getUserFeed(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseFeedPost =
            testObserver.values()[0].last() // get the last feed post

        CommonMockUtilsUser.assertFeedPostEquals(testFeedPost, responseFeedPost)
    }

    //UserStarredPosts end point
    @Test
    fun `get user request network response user starred`() {
        val testUserRequest = getTestFeedPostUserStarred()

        val response = CommonMockUtilsUser.getResponse(false, CommonMockUtilsUser.USER_STARRED)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.getUserStarredPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseUserRequest = testObserver.values()[0].last() // get the last feed post (least recent)

        CommonMockUtilsUser.assertFeedPostEquals(testUserRequest, responseUserRequest)
    }

    //UserRecentPosts end point
    @Test
    fun `get user request network response user recent posts`() {
        val testUserRequest = getTestFeedPostUserStarred()

        val response = CommonMockUtilsUser.getResponse(false, CommonMockUtilsUser.USER_POSTS)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.getUserRecentPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseUserRequest = testObserver.values()[0].last() // get the last feed post (least recent)

        CommonMockUtilsUser.assertFeedPostEquals(testUserRequest, responseUserRequest)
    }

    //UserData end point
    @Test
    fun `get user request network response user data`() {
        val testUserData = getTestUserData()

        val response = CommonMockUtilsUser.getResponse(false, CommonMockUtilsUser.USER_DATA)
        mockWebServer.enqueue(response)
        val testObserver = mockApi.getUserData(USER).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)
        val responseUserData = testObserver.values()[0] // get the last feed post (least recent)

        CommonMockUtilsUser.assertUserDataEquals(testUserData, responseUserData)
    }


    @Test
    fun `get expected size of user feed response`() {
        val response = CommonMockUtilsUser.getModifiedResponse("title", CommonMockUtilsUser.USER_FEED)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserFeed(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(8, testObserver.values()[0].size)
    }

    @Test
    fun `get expected size of user starred posts response`() {
        val response = CommonMockUtilsUser.getModifiedResponse("title", CommonMockUtilsUser.USER_STARRED)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserStarredPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(3, testObserver.values()[0].size)
    }

    @Test
    fun `get expected size of user recent posts response`() {
        val response = CommonMockUtilsUser.getModifiedResponse("title", CommonMockUtilsUser.USER_POSTS)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserRecentPosts(USER, LIMFROM, LIMTO).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(8, testObserver.values()[0].size)
    }

    @Test
    fun `get expected size of user data response`() {
        val response = CommonMockUtilsUser.getModifiedResponse("title", CommonMockUtilsUser.USER_DATA)
        mockWebServer.enqueue(response)

        val testObserver = mockApi.getUserData(USER).test()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        Assert.assertEquals(0, testObserver.values().size)
    }


    companion object {
        const val USER = 1
        const val USEREMAIL = "mirceateodor.oprea@gmail.com"
        const val LIMTO = 20
        const val LIMFROM = 0
    }
}