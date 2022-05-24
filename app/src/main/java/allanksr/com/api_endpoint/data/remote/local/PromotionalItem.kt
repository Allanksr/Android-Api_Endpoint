package allanksr.com.api_endpoint.data.remote.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promotional_items")
data class PromotionalItem(
    var time_was_applied: String = "",
    var total_time: String = "",
    var promotion_type: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)