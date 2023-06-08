package com.example.shopnow.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopnow.data.User
import com.example.shopnow.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    private lateinit var userList : ArrayList<User>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        //var currentUser:User? = null

        mDbRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               //currentUser = snapshot.getValue(User::class.java)

                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid == currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }

                if(userList.isNotEmpty()){
                    binding.tvSplashScreen.text = "Welcome Abroad ${userList[0].firstName} ${userList[0].lastName}\n We will make your shopping experience\nAmazing"
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        binding.tvSplashScreen.alpha = 0f
        binding.tvSplashScreen.animate().setDuration(2500).alpha(1f).withEndAction {
            val i = Intent(this, ShoppingActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}