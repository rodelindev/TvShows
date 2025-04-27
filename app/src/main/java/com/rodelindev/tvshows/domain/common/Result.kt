package com.rodelindev.tvshows.domain.common

/*sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Error<T : Any>(message: String, data: T?=null) : Result<T>()
    class Exception<T : Any>(val exception: kotlin.Exception) : Result<T>()
}*/

/*
sealed class Result<T>(val data:T?=null , val message:String?=null) {
    //Error, Success
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(message: String, data: T?=null) : Result<T>(data,message)
}*/
