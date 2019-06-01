package com.alexiacdura.mn_core

import com.alexiacdura.mn_core.data.models.VoteResponse
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import java.io.File

object CommonMockUtilsVote {
    const val VOTE_RESPONSE = "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/vote_response.json"

    private fun getJson(path: String, malformed: Boolean): String {
        //val uri = this.javaClass.classLoader.getResource(path)
        //val decodedPath = URLDecoder.decode(uri.path, "UTF-8")
        val file = File(path)
        val resourceText = String(file.readBytes())
        return if (malformed) {
            resourceText.substring(2)
        } else {
            resourceText
        }
    }

    fun getResponse(malformed: Boolean, file: String): MockResponse {
        val responseBody = getJson(file, malformed)
        return MockResponse().setBody(responseBody)
    }

    fun getModifiedResponse(attributeToModify: String, file: String): MockResponse {
        val responseBody = getModifiedResponseString(attributeToModify, file)
        return MockResponse().setBody(responseBody)
    }

    private fun getModifiedResponseString(attributeToModify: String, file: String): String {
        val responseBody = getJson(file, false)

        val regex = Regex("\"$attributeToModify\": (\".+\")")
        val match = regex.find(responseBody)

        return responseBody.replaceRange(match?.groups?.get(1)?.range ?: 0..0, "null")
    }

    fun assertVoteResponseEquals(expected: VoteResponse, actual: VoteResponse) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.upvote, actual.upvote)
        Assert.assertEquals(expected.dateVote, actual.dateVote)
        Assert.assertEquals(expected.feedPostIdVote, actual.feedPostIdVote)
        Assert.assertEquals(expected.userIdVote, actual.userIdVote)
    }
}


