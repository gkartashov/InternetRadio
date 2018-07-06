package com.jg.internetradio.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jg.internetradio.R
import com.jg.internetradio.ui.misc.PagerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_activity_pager.adapter = PagerViewAdapter(supportFragmentManager)

        /*binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        binding.categoryListViewModel?.categoryList?.observe(this, Observer { t ->
                binding.textView.text = when(t) {
                    null -> "Nothing found"
                    else -> if (!t?.isEmpty()!!) binding.categoryListViewModel.toString() else "Nothing found"
                }
        })*/
    }
}