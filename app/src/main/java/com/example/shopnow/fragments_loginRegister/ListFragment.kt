package com.example.shopnow.fragments_loginRegister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopnow.R
import com.example.shopnow.activities.products
import com.example.shopnow.adapters.ProductAdapter

class ListFragment : Fragment(R.layout.fragment_list) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View? = inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productList = view.findViewById<RecyclerView>(R.id.product_list).apply {

            layoutManager = LinearLayoutManager(activity)

            adapter = ProductAdapter {

                findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(it.id))
            }
            setHasFixedSize(true)
        }
        (productList.adapter as ProductAdapter).submitList(products)


    }

    override fun onDestroy() {
        requireActivity().finishAffinity()
        super.onDestroy()

    }

}