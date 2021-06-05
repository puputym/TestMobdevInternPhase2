package com.puput.testsuitmediamobdev.event

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puput.testsuitmediamobdev.R
import com.puput.testsuitmediamobdev.adapter.EventAdapter
import com.puput.testsuitmediamobdev.databinding.ActivityEventBinding
import com.puput.testsuitmediamobdev.model.EventModel


class EventActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEventBinding
    private lateinit var eventAdapter: EventAdapter
    private val resultCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRvEvent()
        binding.back.setOnClickListener(this)
        binding.maps.setOnClickListener(this)
    }

    private fun showRvEvent() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[EventViewModel::class.java]
        eventAdapter = EventAdapter()
        eventAdapter.setEvent(viewModel.getEvent())

        binding.rvEvent.apply {
            layoutManager = LinearLayoutManager(this@EventActivity)
            setHasFixedSize(true)
            adapter = eventAdapter

            eventAdapter.setOnItemClickCallback(object : EventAdapter.OnItemClickCallback {
                override fun onItemClicked(data: EventModel) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("EVENT-NAME", data.name)
                    setResult(resultCode, resultIntent)
                    finish()
                }

            })

        }


    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.back ->{
                finish()
            }
            R.id.maps->{
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}