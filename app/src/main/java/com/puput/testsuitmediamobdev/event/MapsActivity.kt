package com.puput.testsuitmediamobdev.event

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.puput.testsuitmediamobdev.R
import com.puput.testsuitmediamobdev.adapter.EventHorizontalAdapter

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var longitude = 0.0
    private var latitude = 0.0
    private val longitudes = doubleArrayOf(
        107.678462,
        107.662451,
        107.677132,
        107.677422,
        107.678248
    )
    private val latitudes = doubleArrayOf(
        -6.920696,
        -6.930235,
        -6.920387,
        -6.919162,
        -6.917362
    )
    private val m: Marker? = null

    private var mPager: ViewPager? = null
    private var mPagerAdapter: PagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mPager = findViewById(R.id.view_pager)
        mPagerAdapter = EventHorizontalAdapter(this)
        mPager!!.adapter = mPagerAdapter
        mPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                longitude = longitudes[position % longitudes.size]
                latitude = latitudes[position % latitudes.size]
                val location = LatLng(latitude, longitude)
                mMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
                mMap?.animateCamera(CameraUpdateFactory.zoomIn())
                mMap?.animateCamera(CameraUpdateFactory.zoomTo(17F), 2000, null)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back -> {
                finish()
            }
            R.id.btn_articel -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        for (i in longitudes.indices) {
            val location = LatLng(latitudes[i], longitudes[i])
            val markerOptions: MarkerOptions =
                MarkerOptions().position(location).title("Event " + (i + 1))
            mMap?.addMarker(markerOptions)
        }
        val location = LatLng(latitudes[mPager!!.currentItem], longitudes[mPager!!.currentItem])
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap?.animateCamera(CameraUpdateFactory.zoomIn())
        mMap?.animateCamera(CameraUpdateFactory.zoomTo(17F), 2000, null)
    }
}