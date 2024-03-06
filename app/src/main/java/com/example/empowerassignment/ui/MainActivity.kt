package com.example.empowerassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empowerassignment.data.beneficiaries
import com.example.empowerassignment.ui.BeneficiaryAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = RecyclerView(this)
        recyclerView.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(recyclerView)


        val adapter = BeneficiaryAdapter(this, beneficiaries)
        recyclerView.adapter = adapter
    }
}