package com.kanchanproseth.goldonecomputershop.service

import com.kanchanproseth.goldonecomputershop.model.User
import retrofit.http.Body
import retrofit.http.Headers
import retrofit.http.POST

/**
 * Created by kanchanproseth on 11/6/17.
 */
interface SignInService{
    @Headers("Content-Type: application/json")
    @POST("/signin")
    fun signIn(@Body body: User): rx.Observable<HashMap<String,String>>
}