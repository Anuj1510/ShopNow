package com.example.shopnow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopnow.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

    }


// ye basicaly check karega ki hamara user already log in hai ya nahi agr already log in hoga to direct main activity
    // khulegi naa ki login page khulega

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if(currentUser != null){
            val intent = Intent(this,ShoppingActivity::class.java)
            startActivity(intent)
        }
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        finish()
//    }
}