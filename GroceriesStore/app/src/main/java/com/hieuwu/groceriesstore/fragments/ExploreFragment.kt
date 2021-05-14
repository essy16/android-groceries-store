package com.hieuwu.groceriesstore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieuwu.groceriesstore.R
import com.hieuwu.groceriesstore.adapters.GridListItemAdapter
import com.hieuwu.groceriesstore.databinding.FragmentExploreBinding


class ExploreFragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentExploreBinding>(
            inflater, R.layout.fragment_explore, container, false
        )
        var dataSet: ArrayList<String> = ArrayList()
        dataSet.add("a")
        dataSet.add("b")
        dataSet.add("a")
        dataSet.add("b")
        dataSet.add("a")
        dataSet.add("b")
        dataSet.add("a")
        dataSet.add("b")
        dataSet.add("a")
        dataSet.add("b")

        var navController = NavHostFragment.findNavController(this)
        var bottomNavView = binding.bottomNavView

        bottomNavView.setupWithNavController(navController)

        setUpRecyclerView(dataSet)
        return binding.root
    }

    private fun setUpRecyclerView(dataSet: ArrayList<String>) {

        val adapter = GridListItemAdapter(dataSet)
        binding.productRecyclerview.layoutManager = GridLayoutManager(this.context, 2)
        binding.productRecyclerview.adapter = adapter
    }
}