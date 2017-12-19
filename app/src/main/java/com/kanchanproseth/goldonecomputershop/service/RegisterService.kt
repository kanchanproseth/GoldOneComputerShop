package com.kanchanproseth.goldonecomputershop.service

import com.kanchanproseth.goldonecomputershop.model.User
import retrofit.Retrofit
import retrofit.http.Body
import retrofit.http.Headers
import retrofit.http.POST

/**
 * Created by kanchanproseth on 11/6/17.
 */
interface RegisterService{
    @Headers("Content-Type: application/json")
    @POST("/register")
    fun register(@Body body: User): rx.Observable<HashMap<String,String>>


}