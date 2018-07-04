package com.jg.internetradio.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jg.internetradio.R
import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.CategoryResponse
import com.jg.internetradio.repository.remote.APIController
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*APIController.instance.apiHandler.getCategories()?.enqueue(object : Callback<MutableList<Category>> {
            override fun onFailure(call: Call<MutableList<Category>>?, t: Throwable?) {
                println(t?.stackTrace)
            }

            override fun onResponse(call: Call<MutableList<Category>>?, response: Response<MutableList<Category>>?) {
                val categories = CategoryResponse(categories = response?.body() ?: throw KotlinNullPointerException("Category list is empty."))
                categories.sortBy(CategoryResponse.SortType.NAME)
                println(categories.categories)
                text_view.text = categories.categories.map { it.title }.toString()
            }
        })*/

    }
}