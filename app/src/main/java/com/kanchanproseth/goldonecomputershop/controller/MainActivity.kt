package com.kanchanproseth.goldonecomputershop.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.model.User
import com.kanchanproseth.goldonecomputershop.service.SignInService
import com.kanchanproseth.goldonecomputershop.util.AESCrypt
import com.kanchanproseth.goldonecomputershop.util.AppBuilder
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    var app = AppBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        app.runConfigure()
        val usernameTextField = signInUsername.text
        val passwordTextfield = signInPassword.text



        signInButton.setOnClickListener {
            if(usernameTextField.isEmpty()){
                Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show()
            }else if (passwordTextfield.isEmpty()){
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }else{
                signin(username = usernameTextField.toString(), password = passwordTextfield.toString())
            }

        }

        registerNowButton.setOnClickListener {
            val myIntent = Intent(this, Register::class.java)
            startActivity(myIntent)
            this.finish()
        }

        //get value from share preference
        val pref = applicationContext.getSharedPreferences("MyPref", 0)
        val username = pref.getString("username", "")//"No name defined" is the default value.
        val password = pref.getString("password", "") //0 is the default value.

        //compare and check user name password
        if (!password.isEmpty() && password != null){
            val pwDecrypt = AESCrypt.decrypt(password)
            if (username != null && pwDecrypt != null){
                autoSignin(username, pwDecrypt)
            }
        }




    }



    fun signin(username: String, password: String){
        val signinService: SignInService = app.retrofit!!.create(SignInService::class.java)
        val model_obj = User()
        model_obj.username = username
        model_obj.password = password

        //retrofit check sign in with api
        signinService.signIn(model_obj) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result["code"] == "200"){
                                //share preference
                                val pref = applicationContext.getSharedPreferences("MyPref", 0)
                                val editor = pref.edit()
                                val pwEncrypt = AESCrypt.encrypt(password)
                                // input value to share pref
                                editor.putString("username", username)
                                editor.putString("password", pwEncrypt)
                                //commit to add value to share preference
                                editor.commit()

                                //go to home
                                val myIntent = Intent(this, HomeActivity::class.java)
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


    fun autoSignin(username: String, password: String){
        val signinService: SignInService = app.retrofit!!.create(SignInService::class.java)
        val model_obj = User()
        model_obj.username = username
        model_obj.password = password

        signinService.signIn(model_obj) .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result["code"] == "200"){


                                val myIntent = Intent(this, HomeActivity::class.java)
                                startActivity(myIntent)
                                this.finish()
                            }else{
                                val pref = applicationContext.getSharedPreferences("MyPref", 0)
                                val editor = pref.edit()
                                editor.clear()
                                editor.commit()
                                Toast.makeText(this, "This Account doesn't existed anymore. please Sign in Again", Toast.LENGTH_SHORT).show()
                            }
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )
    }
}
