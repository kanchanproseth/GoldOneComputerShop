package com.kanchanproseth.goldonecomputershop.service

import com.kanchanproseth.goldonecomputershop.model.BlogModel
import retrofit.http.GET
import retrofit.http.Headers

/**
 * Created by kanchanproseth on 12/19/17.
 */

interface Blogservice{
    @Headers("Content-Type: application/json")
    @GET("/Blog")
    fun getAllBlogs(): rx.Observable<BlogModel>
}