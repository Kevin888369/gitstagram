package com.example.gitstagram.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitstagram.network.GitUser

@Database(
    entities = [GitUser::class],
    version = 2
)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): DaoUser
    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "git_user_database")
                        .build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}


