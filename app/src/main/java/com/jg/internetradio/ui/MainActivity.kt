package com.jg.internetradio.ui

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jg.internetradio.R
import android.databinding.DataBindingUtil
import android.arch.lifecycle.ViewModelProviders
import com.jg.internetradio.databinding.ActivityMainBinding
import com.jg.internetradio.viewmodel.CategoryListViewModel

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        binding.categoryListViewModel?.addObserver(this, Observer { t ->
                binding.textView.text = if (!t?.isEmpty()!!) binding.categoryListViewModel.toString() else "Nothing found"
        })
    }
}