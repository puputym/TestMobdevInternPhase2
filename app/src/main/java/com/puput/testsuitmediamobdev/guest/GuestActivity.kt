package com.puput.testsuitmediamobdev.guest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.puput.testsuitmediamobdev.R
import com.puput.testsuitmediamobdev.adapter.GuestAdapter
import com.puput.testsuitmediamobdev.databinding.ActivityGuestBinding
import com.puput.testsuitmediamobdev.event.MapsActivity
import com.puput.testsuitmediamobdev.model.GuestModel


class GuestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGuestBinding
    private lateinit var guestAdapter: GuestAdapter
    private val resultCode = 1
    private var page =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGuest.setHasFixedSize(true)
        showRvGuest()
        refresh()
        binding.back.setOnClickListener(this)
        binding.maps.setOnClickListener(this)
    }


    private fun showRvGuest() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[GuestViewModel::class.java]
        guestAdapter = GuestAdapter()

        viewModel.setDataGuest()
        viewModel.getDataGuest().observe(this) {
            guestAdapter.setGuest(it)
            binding.apply {
                rvGuest.layoutManager = GridLayoutManager(this@GuestActivity, 2)
                rvGuest.adapter = guestAdapter
                guestAdapter.setOnItemClickCallback(object : GuestAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: GuestModel) {

                        val guestBirthDate = data.birthdate

                        val date = guestBirthDate.split("-".toRegex()).toTypedArray()
                        val birthDate = date[2].toInt()
                        val monthBirthdayGuest: Int = date[1].toInt()

                        val day = if (birthDate % 2 == 0 && birthDate % 3 == 0) {
                            "iOS"
                        } else if (birthDate % 2 == 0) {
                            "blackberry"
                        } else if (birthDate % 3 == 0) {
                            "android"
                        } else {
                            "feature phone"
                        }

                        val month = if (isPrime(monthBirthdayGuest)) {
                            "is prima"
                        } else {
                            "not prima"
                        }

                        val toastMessage = "$day dan $month"
                        Toast.makeText(baseContext, toastMessage, Toast.LENGTH_LONG).show()

                        val resultIntent = Intent()
                        resultIntent.putExtra("GUEST-NAME", data.name)

                        setResult(resultCode, resultIntent)
                        finish()
                    }
                })
                refreshView.setOnRefreshListener {
                    refresh()
                    refreshView.isRefreshing = false
                }
            }
        }

    }
    private fun isPrime(month: Int): Boolean {
        return when (month) {
            2, 3, 5, 7, 11 -> true
            else -> false
        }
    }

    private fun refresh() {
        Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();

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