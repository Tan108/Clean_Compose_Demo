package com.tan.core.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptors : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = "h19ORBBbaoX7hi5ClgZwq4vj6xhqu5wVn86PNREiLZB4dyzdI62hL0h8o3gw"
        request.addHeader("Authorization", token)
        request.addHeader("Accept","application/json")
        return chain.proceed(request.build())
    }
}