package com.example.shopnow.fragments_loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.shopnow.R
import com.example.shopnow.activities.ShoppingActivity
import com.example.shopnow.activities.SplashScreenActivity
import com.example.shopnow.databinding.FragmentIntroductionBinding
import com.example.shopnow.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment:Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding.buttonLoginLogin.setOnClickListener {

            val email = binding.edEmailLogin.text.toString()
            val password = binding.edPasswordLogin.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(activity, "Please fill the details", Toast.LENGTH_SHORT).show()
            }else{
                login(email,password)
            }

        }

        binding.tvForgotPasswordLogin.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_resetPasswordFragment)

        }


    }

    private fun login(email: String, password: String) {

        activity?.let {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val verification = mAuth.currentUser?.isEmailVerified // email verification
                        if(verification == true){
                            val intent = Intent(activity,SplashScreenActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(activity,"Please verify your email",Toast.LENGTH_LONG).show()
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activity,"User does not exist", Toast.LENGTH_SHORT).show()
                        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_loginFragment_to_registerFragment) }
                    }
                }
        }

    }

}