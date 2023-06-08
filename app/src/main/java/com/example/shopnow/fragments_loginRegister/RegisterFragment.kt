package com.example.shopnow.fragments_loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.shopnow.R
import com.example.shopnow.data.User
import com.example.shopnow.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


class RegisterFragment:Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()


        binding.buttonRegisterRegister.setOnClickListener {

            val firstName = binding.edFirstNameRegister.text.toString()
            val lastName = binding.edLastNameRegister.text.toString()
            val email = binding.edEmailRegister.text.toString()
            val password = binding.edPasswordRegister.text.toString()

            if(firstName.isEmpty()|| lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(activity, "Please fill the details", Toast.LENGTH_SHORT).show()
            }else{
                signUp(email,password,firstName,lastName)
            }





        }

    }

    private fun signUp(email: String, password: String, firstName: String, lastName: String) {
        activity?.let {
            mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // ye check karega ki email id already register hai ya nahi
                        val signInMethods = task.result?.signInMethods // List of sign-in methods for the email ID
                        val isEmailRegistered = signInMethods?.isNotEmpty() ?: false

                        if (isEmailRegistered) {
                            // Email ID is already registered
                            Toast.makeText(context, "Email ID is already registered", Toast.LENGTH_SHORT).show()
                        } else {
                            // agr not register then proceed
                            mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(it) { Task ->
                                    if (Task.isSuccessful) {
                                        // Sign in success, update UI with the sig6ned-in user's information
                                        mAuth.currentUser?.sendEmailVerification() // email verification
                                            ?.addOnSuccessListener {
                                                Toast.makeText(activity,"Please verify your email",Toast.LENGTH_LONG).show()
                                            }
                                            ?.addOnFailureListener {
                                                Toast.makeText(activity,"Some error occurred",Toast.LENGTH_LONG).show()
                                            }
                                        addUserToDatabase(email, mAuth.currentUser?.uid!!,firstName,lastName)
                                        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_registerFragment_to_loginFragment) }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(activity, "Some error occurred. Please check your internet connection", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    } else {
                        // Error occurred while checking the email ID
                        Toast.makeText(context, "Error occurred while checking email ID", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun addUserToDatabase(email: String, uid: String, firstName: String, lastName: String) {

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(firstName,lastName,email, uid))

    }

}