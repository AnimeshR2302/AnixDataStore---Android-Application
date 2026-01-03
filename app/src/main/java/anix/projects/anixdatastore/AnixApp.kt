package anix.projects.anixdatastore

import android.app.Application
import androidx.room.Room
import anix.projects.anixdatastore.data.AnixDatabase

class AnixApp : Application() {
    companion object {
        lateinit var database: AnixDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AnixDatabase::class.java,
            "anix_db"
        ).build()
    }
}
