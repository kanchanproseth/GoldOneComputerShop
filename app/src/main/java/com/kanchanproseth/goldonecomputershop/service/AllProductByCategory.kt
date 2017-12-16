package com.kanchanproseth.goldonecomputershop.service

import com.kanchanproseth.goldonecomputershop.model.MyData
import retrofit.http.GET
import retrofit.http.Headers

/**
 * Created by kanchanproseth on 12/15/17.
 */
interface AllProductByCategory {
    @Headers("Content-Type: application/json")
    @GET("/Product")
    fun getAllProductByCategory(): rx.Observable<MyData>
}