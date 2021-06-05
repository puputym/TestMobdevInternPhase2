package com.puput.testsuitmediamobdev.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.puput.testsuitmediamobdev.DummyEvent
import com.puput.testsuitmediamobdev.R
import com.puput.testsuitmediamobdev.databinding.ListEventHorizontalBinding


class EventHorizontalAdapter(var context: Context) : PagerAdapter() {


    private var listEventHorizontal = DummyEvent.generateEventData()


    override fun getCount(): Int = listEventHorizontal.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = mLayoutInflater.inflate(R.layout.list_event_horizontal, container, false)
        val itemHorizontalBinding =
            ListEventHorizontalBinding.inflate(
                    LayoutInflater.from(container.context),
                    container,
                    false
            )

        val model = listEventHorizontal[position]
        val name = model.name


        Log.d("event 123", listEventHorizontal[position].toString())

        Glide.with(context)
            .load(model.img)
            .apply(RequestOptions().override(150, 150))
            .into(itemHorizontalBinding.tvImg)
        itemHorizontalBinding.eventName.text = name

        container.addView(view)
        return view
    }
}