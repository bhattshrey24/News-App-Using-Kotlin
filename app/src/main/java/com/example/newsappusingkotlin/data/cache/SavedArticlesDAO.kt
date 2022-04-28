package com.example.newsappusingkotlin.data.cache

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavedArticlesDAO {
    // 3 functions , insert , delete and get all articles

    // room will generate all the necessary code for dao therefore we use interface cause we just have to define methods and there body will be given by room
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(savedNews: SavedNewsEntity?):Long // you can change the name from 'insert' to any name u like , I used it for simplicity

    @Delete
    suspend fun deleteArticle(savedNews: SavedNewsEntity?)

    @Query("SELECT * FROM saved_news_table ORDER BY id DESC ")
    fun getAllSavedNewsArticles(): LiveData<List<SavedNewsEntity>> // this returns all the saved news objects from the table and convert them to list as well

    @Query("DELETE FROM saved_news_table")
    fun deleteNewsArticleTable()// this returns all the saved news objects from the table and convert them to list as well


}