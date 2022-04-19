package com.example.newsappusingkotlin.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// This is how we apply singleton pattern
@Database(entities = [SavedNewsEntity::class], version = 1)
abstract class MyDatabase :
    RoomDatabase() { // abstract class so that no one can make object of this class

    abstract fun savedNewsArticlesDao(): SavedArticlesDAO// providing instance of our DAO

    companion object {// companion object is like static ie. all fields mentioned inside it our static

        @Volatile// what this does is that whenever the below variable(ie. INSTANCE variable) gets assigned a value , all the threads will get notified about it and they get the updated value
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {// only this is accessible to outside classes since its public
            if (INSTANCE == null) {
                synchronized(this) {// this is how we provide thread safety , here we are giving class level lock i guess
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "saved_news_articles_db"
                    ).build()
                }
                // THE NAME MENTIONED HERE IS THE NAME OF THE WHOLE DATABSE I.E"NEWS_APP_DATABASE"
            }
            return INSTANCE!! // we are sure that this will never be null , ie. if it was null then above if will be executed and it will get the database object and if its already filled then simply return it
        }
    }

}