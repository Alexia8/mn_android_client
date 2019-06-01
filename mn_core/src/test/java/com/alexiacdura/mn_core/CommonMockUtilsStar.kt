package com.alexiacdura.mn_core

import com.alexiacdura.mn_core.data.models.StarResponse
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import java.io.File

object CommonMockUtilsStar {
    const val STAR_RESPONSE = "/Users/alexiacdura/Git/mn_android_client/mn_core/src/test/java/resources/star_response.json"

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

    fun assertStarResponseEquals(expected: StarResponse, actual: StarResponse) {
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.dateStar, actual.dateStar)
        Assert.assertEquals(expected.feedPostIdStar, actual.feedPostIdStar)
        Assert.assertEquals(expected.userIdStar, actual.userIdStar)
    }
}


