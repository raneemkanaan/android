package com.penguin.floor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.penguin.floor.databinding.ActivityMainBinding
import com.penguin.floor.remote.RetrofitClient
import com.penguin.floor.remote.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerViewFloors.layoutManager = LinearLayoutManager(this)


        val api = RetrofitClient.instance.create(ServiceApi::class.java)
        val call: Call<List<Floor>> = api.getFloors()
        call.enqueue(object : Callback<List<Floor>> {
            override fun onResponse(call: Call<List<Floor>>, response: Response<List<Floor>>) {
                if (response.isSuccessful) {
                    val floors = response.body()
                    Log.d("MainActivity", "Floors: $floors")


                    floors?.firstOrNull()?.let { firstFloor ->
                        binding.textViewFloor.text = firstFloor.name
                    }


                    floors?.let {
                        val adapter = FloorAdapter(it)
                        binding.recyclerViewFloors.adapter = adapter
                    }

                } else {
                    Log.e("MainActivity", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Floor>>, t: Throwable) {
                Log.e("MainActivity", "Network error", t)
            }
        })
    }
}
