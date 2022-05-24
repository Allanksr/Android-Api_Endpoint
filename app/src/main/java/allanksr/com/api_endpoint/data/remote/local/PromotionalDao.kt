package allanksr.com.api_endpoint.data.remote.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PromotionalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotionalItem(promotionalItem: PromotionalItem)

    @Delete
    suspend fun deletePromotionalItem(promotionalItem: PromotionalItem)

    @Query("SELECT * FROM promotional_items")
    fun observeAllPromotionalItems(): LiveData<List<PromotionalItem>>


}