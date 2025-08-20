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
    private lateinit var adapter: FloorAdapter
    private lateinit var api: ServiceApi
    private var floors: MutableList<Floor> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView setup
        binding.recyclerViewFloors.layoutManager = LinearLayoutManager(this)

        api = RetrofitClient.instance.create(ServiceApi::class.java)

        adapter = FloorAdapter(
            floors,
            onUpdateClick = { floor -> updateFloor(floor) },
            onDeleteClick = { floor -> deleteFloor(floor) }
        )

        binding.recyclerViewFloors.adapter = adapter

        // Load data
        loadFloorsFromApi()

        // Save button
        binding.btnSave.setOnClickListener {
            val floorName = binding.editFloorName.text.toString().ifEmpty { "Unnamed Floor" }

            val newFloor = Floor(
                id = null,
                name = floorName,
                level = 1,
                isDeleted = false,
                venueId = 1
            )
            saveFloorToApi(newFloor)
        }
    }

    private fun loadFloorsFromApi() {
        api.getFloors().enqueue(object : Callback<List<Floor>> {
            override fun onResponse(call: Call<List<Floor>>, response: Response<List<Floor>>) {
                if (response.isSuccessful) {
                    floors.clear()
                    response.body()?.let { floors.addAll(it) }
                    adapter.updateData(floors)
                    Log.d("MainActivity", "Floors loaded: $floors")
                } else {
                    Log.e("MainActivity", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Floor>>, t: Throwable) {
                Log.e("MainActivity", "Network error", t)
            }
        })
    }

    private fun saveFloorToApi(floor: Floor) {
        api.saveFloor(floor).enqueue(object : Callback<Floor> {
            override fun onResponse(call: Call<Floor>, response: Response<Floor>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        floors.add(it)
                        adapter.updateData(floors)
                        binding.editFloorName.text.clear()
                        Log.d("MainActivity", "Floor saved: $it")
                    }
                } else {
                    Log.e("MainActivity", "Save failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Floor>, t: Throwable) {
                Log.e("MainActivity", "Save error", t)
            }
        })
    }

    private fun updateFloor(floor: Floor) {
        floor.name = "Updated Name" // مؤقت للتجربة
        api.updateFloor(floor.id ?: return, floor).enqueue(object : Callback<Floor> {
            override fun onResponse(call: Call<Floor>, response: Response<Floor>) {
                if (response.isSuccessful) {
                    val index = floors.indexOfFirst { it.id == floor.id }
                    if (index != -1) {
                        floors[index] = response.body()!!
                        adapter.updateData(floors)
                    }
                    Log.d("MainActivity", "Floor updated: ${response.body()}")
                } else {
                    Log.e("MainActivity", "Update failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Floor>, t: Throwable) {
                Log.e("MainActivity", "Update error", t)
            }
        })
    }

    private fun deleteFloor(floor: Floor) {
        api.deleteFloor(floor.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    floors.remove(floor)
                    adapter.updateData(floors)
                    Log.d("MainActivity", "Floor deleted: ${floor.id}")
                } else {
                    Log.e("MainActivity", "Delete failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("MainActivity", "Delete error", t)
            }
        })
    }
}
