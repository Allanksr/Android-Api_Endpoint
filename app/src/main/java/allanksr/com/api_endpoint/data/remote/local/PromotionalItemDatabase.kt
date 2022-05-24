package allanksr.com.api_endpoint.data.remote.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PromotionalItem::class],
    version = 1
)
abstract class PromotionalItemDatabase : RoomDatabase() {

    abstract fun promotionalDao(): PromotionalDao
}