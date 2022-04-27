package com.example.newsappusingkotlin.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.cache.MyDatabase
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.data.remote.repository.LocalRepository
import com.example.newsappusingkotlin.other.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelForCache(application: Application) :
    AndroidViewModel(application) { // AndroidViewModel is different from normal View model because it contains application reference

    private val getAllNewsArticles: LiveData<List<SavedNewsEntity>>
    private val repository: LocalRepository
    private val db = FirebaseFirestore.getInstance()

    init {
        val savedArticlesDAO = MyDatabase.getDatabase(application)
            .savedNewsArticlesDao() // we are getting our DAO from our Database class
        repository = LocalRepository(savedArticlesDAO)
        getAllNewsArticles = repository.getAllSavedNewsArticles
    }

    fun getAllArticles(): LiveData<List<SavedNewsEntity>> {
        return getAllNewsArticles
    }

    fun addNewsArticle(newsArticle: SavedNewsEntity) { // this sends data to Room as well as FireStore
        viewModelScope.launch(Dispatchers.IO) {
            repository.addArticle(newsArticle) // the Insert Annotation returns the Id with which it is saved in Database
        }
    }

     fun sendNewsToFireStore(news: SavedNewsEntity) : Task<DocumentReference> {
        val newsHM: MutableMap<String, Any?> =
            HashMap() // Any? means it can be any datatype and can be null too

        // newsHM[Constants.newsSourceFSKey] = news.source
        newsHM[Constants.newsAuthorFSKey] = news.author
        newsHM[Constants.newsTitleFSKey] = news.title
        newsHM[Constants.newsDescriptionFSKey] = news.description
        newsHM[Constants.newsUrlToArticleFSKey] = news.urlToArticle
        newsHM[Constants.newsUrlToImageFSKey] = news.urlToImage
        newsHM[Constants.newsPublishedAtFSKey] = news.publishedAt
        newsHM[Constants.newsContentFSKey] = news.content

        val docId = FirebaseAuth.getInstance().currentUser?.uid
        //  val docReference=

        // here make the document Id same as the Id given by Room Database , so first save data in room then using the ID save data in Firestore , this way we can delete data from both database easily since evey article will have same id in room as in firestore
       return db.collection(Constants.userCollectionFSKey).document(docId!!).collection(Constants.userArticleDocumentFSKey)
            .add(newsHM)
    }


    fun deleteNewsArticle(newsArticle: SavedNewsEntity) {// this deletes from Room DB as well as Firestore DB
        viewModelScope.launch(Dispatchers.IO) {
           repository.deleteArticle(newsArticle)
            val docId = FirebaseAuth.getInstance().currentUser?.uid
            val articleIdString=newsArticle.id.toString()
            db.collection(Constants.userCollectionFSKey).document(docId!!).collection(Constants.userArticleDocumentFSKey).document(articleIdString).delete()
        }
    }

}