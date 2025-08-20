package com.penguin.floor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.penguin.floor.databinding.ActivityMain2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.penguin.floor.local.AppDatabase
import com.penguin.floor.repository.Repository
import com.penguin.floor.remote.RetrofitInstance
import com.penguin.floor.local.VenueEntity

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var adapter: FloorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // إعداد RecyclerView
        adapter = FloorAdapter(
            emptyList(),
            onUpdateClick = { floor -> updateFloor(floor) },
            onDeleteClick = { floor -> deleteFloor(floor) }
        )

        binding.recyclerViewFloors.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFloors.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val venueDao = db.venueDao()
            val floorDao = db.floorDao()

            val venue = VenueEntity(name = "City Mall")
            venueDao.insert(venue)

            val floor = Floor(
                name = "Main Floor",
                level = 1,
                isDeleted = false,
                venueId = 1
            )
            floorDao.insert(floor)

            val floors = floorDao.getAllFloors()
            runOnUiThread {
                adapter.updateData(floors)
            }
        }

        val repository = Repository(
            AppDatabase.getDatabase(applicationContext),
            RetrofitInstance.api
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.fetchFloorsFromApi()
                val floors = repository.getFloorsFromDb()
                runOnUiThread {
                    adapter.updateData(floors)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.btnSave.setOnClickListener {
            val floorName = binding.editFloorName.text.toString()
            if (floorName.isNotBlank()) {
                val newFloor = Floor(
                    name = floorName,
                    level = 1,
                    isDeleted = false,
                    venueId = 1
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val db = AppDatabase.getDatabase(applicationContext)
                    db.floorDao().insert(newFloor)
                    val floors = db.floorDao().getAllFloors()
                    runOnUiThread {
                        adapter.updateData(floors)
                        binding.editFloorName.text.clear()
                    }
                }
            }
        }
    }

    private fun updateFloor(floor: Floor) {
        println("Update clicked: ${floor.name}")
    }

    private fun deleteFloor(floor: Floor) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            db.floorDao().delete(floor)
            val floors = db.floorDao().getAllFloors()
            runOnUiThread {
                adapter.updateData(floors)
            }
        }
    }
}
