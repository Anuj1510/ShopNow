package com.example.shopnow.fragments_loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shopnow.R
import com.example.shopnow.databinding.FragmentResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordFragment:Fragment(R.layout.fragment_reset_password) {

    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentResetPasswordBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        // jub tuk email verify waali functionality on nahi hogi tub tuk reset password kaam nahi karega
        binding.buttonResetReset.setOnClickListener {
            // to reset password
            val vEmail = binding.edEmailReset.text.toString()
            mAuth.sendPasswordResetEmail(vEmail)
                .addOnSuccessListener {
                    Toast.makeText(activity,"Please check your email",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(activity,"Some error has occurred",Toast.LENGTH_LONG).show()
                }
        }
    }

}