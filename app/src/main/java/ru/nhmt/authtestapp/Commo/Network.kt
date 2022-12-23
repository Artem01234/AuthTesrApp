package ru.nhmt.authtestapp.Commo

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

object Network {

    const val BASE_URL = "http://172.16.0.100"


    const val URL_USER_LIST = "$BASE_URL/auth/user/list"
    const val URL_USER_LIST1 = "$BASE_URL/auth/user/list"
    const val URL_USER_LOGIN = "$BASE_URL/auth/login"
    const val URL_USER_REGISTER = "$BASE_URL/auth/register"

    val httpClient = HttpClient(){
        install(ContentNegotiation){
            gson()
        }
    }

}