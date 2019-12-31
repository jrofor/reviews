package com.example.roman.reviews.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor constructor(var apiKey: String) : Interceptor {

    companion object {
        private const val PARAM_API_KEY = "api-key"
        fun create(apiKey: String): Interceptor {
            return ApiKeyInterceptor(apiKey)
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithoutApiKey: Request = chain.request()

        val url: HttpUrl = requestWithoutApiKey.url()
            .newBuilder()
            .addQueryParameter(PARAM_API_KEY, apiKey)
            .build()

        val requestWithAttachedApiKey: Request = requestWithoutApiKey.newBuilder()
            .url(url)
            .build()

        return chain.proceed(requestWithAttachedApiKey)
    }
}