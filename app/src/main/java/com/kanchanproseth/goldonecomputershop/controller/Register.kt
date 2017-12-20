package com.kanchanproseth.goldonecomputershop.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.model.User
import com.kanchanproseth.goldonecomputershop.service.RegisterService
import com.kanchanproseth.goldonecomputershop.util.AppBuilder
import kotlinx.android.synthetic.main.activity_register.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Register : AppCompatActivity() {
    var app = AppBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar!!.hide()
        setContentView(R.layout.activity_register)
        app.runConfigure()
        val username = registerUsername.text
        val password = registerPassword.text
        registerButton.setOnClickListener {
            register(username = username.toString(),password = password.toString())
        }

    }

    fun register(username: String, password: String){
        val registerService: RegisterService = app.retrofit!!.create(RegisterService::class.java)
        val model_obj = User()
        model_obj.username = username
        model_obj.password = password

        registerService.register(model_obj) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result["code"] == "200"){
                                Toast.makeText(this, result["message"], Toast.LENGTH_LONG).show()
                                val myIntent = Intent(this, MainActivity::class.java)
                                startActivity(myIntent)
                                this.finish()
                            }else{
                                Toast.makeText(this, result["message"], Toast.LENGTH_SHORT).show()
                            }
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )
    }
}
