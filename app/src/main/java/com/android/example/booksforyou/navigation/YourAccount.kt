package com.android.example.booksforyou.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.example.booksforyou.*
import com.android.example.booksforyou.databinding.FragmentYourAccountBinding
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback
import com.facebook.GraphRequest
import org.json.JSONException
import com.facebook.AccessToken

import com.facebook.AccessTokenTracker





class YourAccount : Fragment() {
    private lateinit var binding: FragmentYourAccountBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentYourAccountBinding>(
            inflater,
            R.layout.fragment_your_account, container, false
        )

        val accessTokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                Log.i("FB_Login", "onCurrentAccessTokenChanged()")
                if (oldAccessToken == null) {
                    // Log in Logic
                } else if (currentAccessToken == null) {
                    // Log out logic
                    Log.i("FB_Login", "userLoggedOut")
                    binding.helloUserText.visibility = View.GONE
                    binding.accountText.text = getString(R.string.no_account)
                    binding.accountAdressText.visibility = View.GONE

                }
            }
        }

        if(userId != null) {
            binding.helloUserText.text = "Hello, $userName!"
            binding.helloUserText.visibility = View.VISIBLE
            binding.accountText.text = getString(R.string.current_account)
            binding.accountAdressText.text = userName
            binding.accountAdressText.visibility = View.VISIBLE
        }

        val loginButton = binding.loginButton
        loginButton.setPermissions("email")
        loginButton.fragment = this
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onCancel() {
                Log.i("FB_Login", "canceled")
            }

            override fun onError(error: FacebookException) {
                Log.i("FB_Login", "error: $error")
            }

            override fun onSuccess(result: LoginResult?) {
                userId = result?.accessToken?.userId
                Log.i("FB_Login", "successful -  userId $userId")

                val request = GraphRequest.newMeRequest(
                    result?.accessToken
                ) { fbObject, _ ->
                    try {

                        Log.i("FB_Login", "successful graph request $fbObject")

                        userName = fbObject?.getString("name")

                        Log.i("FB_Login",   "user name $userName")
                        startActivity(Intent(context, MainActivity::class.java))

                        binding.helloUserText.text = "Hello, $userName!"
                        binding.helloUserText.visibility = View.VISIBLE
                        binding.accountText.text = getString(R.string.current_account)
                        binding.accountAdressText.text = userName
                        binding.accountAdressText.visibility = View.VISIBLE

                        Toast.makeText(activity, "Successfully logged in!", Toast.LENGTH_SHORT).show()
                        accessTokenTracker.startTracking()

                    } //If no data has been retrieve throw some error
                    catch (e: JSONException) {
                        Log.i("FB_Login", "an error occurred")
                    }

                }

                request.executeAsync()

            }

        })

        return binding.root
    }
}