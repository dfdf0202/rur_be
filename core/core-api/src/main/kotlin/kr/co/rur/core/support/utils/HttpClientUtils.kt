package kr.co.rur.core.support.utils

import org.apache.http.HttpStatus
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.io.IOException

object HttpClientUtils {

    fun doGet(url: String, headers: Map<String, String>?): String {
        val httpClient = HttpClients.createDefault()

        val requestConfig = RequestConfig.custom()
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000)
            .build()

        var resultString = ""
        var response: CloseableHttpResponse? = null

        try {
            val builder = URIBuilder(url)

            val uri = builder.build()
            val httpGet = HttpGet(uri).apply {
                config = requestConfig
                setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2")
                headers?.forEach { (key, value) ->
                    addHeader(key, value)
                }
            }

            response = httpClient.execute(httpGet)
            if (response.statusLine.statusCode == 200) {
                resultString = EntityUtils.toString(response.entity, "UTF-8")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                response?.close()
                httpClient.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return resultString
    }

    fun doPost(url: String, param: Map<String, String>?): String {
        val httpClient = HttpClients.createDefault()

        val requestConfig = RequestConfig.custom()
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000)
            .build()

        var resultString = ""
        var response: CloseableHttpResponse? = null

        try {
            val httpPost = HttpPost(url).apply {
                config = requestConfig
                if (param != null) {
                    val paramList: List<NameValuePair> = param.map { (key, value) ->
                        BasicNameValuePair(key, value)
                    }
                    entity = UrlEncodedFormEntity(paramList)
                }
            }

            response = httpClient.execute(httpPost)
            println("response : " + response)
            if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                resultString = EntityUtils.toString(response.entity, "UTF-8")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                response?.close()
                httpClient.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return resultString
    }
}