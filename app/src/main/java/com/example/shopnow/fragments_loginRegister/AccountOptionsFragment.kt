package com.example.shopnow.fragments_loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.shopnow.R
import com.example.shopnow.databinding.FragmentAccountOptionsBinding
import com.example.shopnow.databinding.FragmentIntroductionBinding

class AccountOptionsFragment:Fragment(R.layout.fragment_account_options) {

    private lateinit var binding: FragmentAccountOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegisterAccountOptions.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_accountOptionsFragment_to_registerFragment)

        }

        binding.buttonLoginAccountOptions.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.action_accountOptionsFragment_to_loginFragment)

        }

    }

}