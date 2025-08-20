package com.penguin.floor.repository

import com.penguin.floor.Floor
import com.penguin.floor.local.AppDatabase
import com.penguin.floor.remote.ServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val db: AppDatabase,
    private val api: ServiceApi
) {
    // جلب من API وحفظ في DB
    suspend fun fetchFloorsFromApi() {
        withContext(Dispatchers.IO) {
            val response = api.getFloors().execute()
            if (response.isSuccessful) {
                response.body()?.let { floors ->
                    db.floorDao().insertAll(floors)
                }
            }
        }
    }

    // قراءة من DB
    suspend fun getFloorsFromDb(): List<Floor> {
        return withContext(Dispatchers.IO) {
            db.floorDao().getAllFloors()
        }
    }

    // إضافة Floor جديد
    suspend fun insertFloor(floor: Floor) {
        withContext(Dispatchers.IO) {
            db.floorDao().insert(floor)
        }
    }

    // تحديث Floor
    suspend fun updateFloor(floor: Floor) {
        withContext(Dispatchers.IO) {
            db.floorDao().update(floor)
        }
    }

    // حذف Floor
    suspend fun deleteFloor(floor: Floor) {
        withContext(Dispatchers.IO) {
            db.floorDao().delete(floor)
        }
    }
}
