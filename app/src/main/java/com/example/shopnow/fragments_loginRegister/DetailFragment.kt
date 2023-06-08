package com.example.shopnow.fragments_loginRegister

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import com.example.shopnow.R
import com.example.shopnow.activities.products
import com.example.shopnow.data.Product
import com.example.shopnow.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = FragmentDetailBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var product: Product? = null
        arguments?.let { it ->
            val args = DetailFragmentArgs.fromBundle(it)
            product = products.find { args.id == it.id }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_listFragment)
        }

        product?.let {
            with(it) {
                binding.productName.text = name
                binding.productPrice.text = price
                binding.productFullDescription.text = longDescription
                binding.productImage.setImageResource(imageId)

                binding.virtual.setOnClickListener {
                    val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                    val intentUri =
                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                            .appendQueryParameter("file", modelURL)
                            .appendQueryParameter("mode", "ar_only")
                            .appendQueryParameter("resizable", "false")
                            .appendQueryParameter("title", "$name - ₹$price")
                            .build()
                    sceneViewerIntent.data = intentUri
                    //sceneViewerIntent.setPackage("com.google.ar.core")

                    /*
                    Check if the com.google.ar.core package is available on the user's device.
                    Before launching the AR experience with the Scene Viewer, you're setting the package
                    explicitly using setPackage("com.google.ar.core"). However, it's possible that the
                    package name may differ on some devices or ARCore versions.
                    to handle this, you can remove the line sceneViewerIntent.setPackage("com.google.ar.core")
                    and let the system choose the appropriate AR viewer app installed on the user's device
                    automatically. This allows for better compatibility across different devices and AR viewer apps.
                    */

                    startActivity(sceneViewerIntent)
                }

                binding.addToCart.setOnClickListener {
                    Toast.makeText(context, "Product added to cart!", Toast.LENGTH_SHORT).show()
                }

                binding.buyNow.setOnClickListener {
                    Toast.makeText(
                        context,
                        "Thank-you for ordering this product!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }





    }
}