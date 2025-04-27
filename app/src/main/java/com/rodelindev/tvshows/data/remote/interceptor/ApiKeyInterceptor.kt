package com.rodelindev.tvshows.data.remote.interceptor


import com.rodelindev.tvshows.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .url(
                        request().url.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .build()
                    ).build()
            )
        }
    }
}
