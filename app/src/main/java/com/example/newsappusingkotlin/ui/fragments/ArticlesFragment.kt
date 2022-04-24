package com.example.newsappusingkotlin.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.adapters.NewsListRecyclerAdapter
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentArticlesBinding
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.viewmodels.ArticlesPageViewModel
import com.example.newsappusingkotlin.ui.viewmodels.ArticlesPageViewModelFactory
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory


class ArticlesFragment : Fragment(), NewsListRecyclerAdapter.OnBookmarkButtonListener {

    private val binding: FragmentArticlesBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesBinding.inflate(layoutInflater, null, false)
    }
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NewsListRecyclerAdapter? = null
    private lateinit var viewModel: ArticlesPageViewModel
    private lateinit var viewModelForCache: ViewModelForCache
    private val repository = MyRepository()

    private var category: String = "business"
// The error is how api is calling and how Im setting , basically konsa lifecycle function kb chlra hai api ke saath kaise sync krna

    //    init {
//        Log.d(
//            Constants.currentDebugTag,
//            "Inside Init of Articles Frag , Category= $category and tag ${this.tag}"
//        )
//    }
//
//
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Constants.currentDebugTag, "Inside OnCreate() of Articles Frag , Category= $category")
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        Log.d(Constants.currentDebugTag, "Inside OnAttach() of Articles Frag , Category= $category")
        super.onAttach(context)
    }

    override fun onStart() {
        Log.d(Constants.currentDebugTag, "Inside OnStart() of Articles Frag , Category= $category")
        super.onStart()
    }

    override fun onPause() {
        Log.d(Constants.currentDebugTag, "Inside OnPause() of Articles Frag , Category= $category")
        super.onPause()
    }

    //
    override fun onResume() { // because On resume is called everytime tab changes and not onCreate

        // callingForNewsArticles() //Fix This , Here only Update RecyclerView , Don't Call Api for Response

        Log.d(Constants.currentDebugTag, "Inside OnResume() of Articles Frag , Category= $category")

        Log.d(
            Constants.currentDebugTag,
            "Inside OnResume() and is listOfNewsArticle null ? ${viewModel.listOfNewsArticle.isNullOrEmpty()}  "
        )
        Log.d(
            Constants.currentDebugTag,
            "Inside OnResume() and is listOfNewsArticleCat1 null ? ${viewModel.listOfNewsArticleCat1.isNullOrEmpty()}  "
        )
        Log.d(
            Constants.currentDebugTag,
            "Inside OnResume() and is listOfNewsArticleCat2 null ? ${viewModel.listOfNewsArticleCat2.isNullOrEmpty()}  "
        )
        val isEmptyForCat1 = viewModel.listOfNewsArticleCat1.isNullOrEmpty()
        val isEmptyForCat2 = viewModel.listOfNewsArticleCat2.isNullOrEmpty()
        val isEmptyForElse = viewModel.listOfNewsArticle.isNullOrEmpty()
        if (!isEmptyForElse) {
            Log.d(
                Constants.currentDebugTag,
                "Inside OnResume() and title of 1st ele of listOfNewsArticle is : ${viewModel.listOfNewsArticle[0].title}  "
            )
        }
        if (!isEmptyForCat1) {
            Log.d(
                Constants.currentDebugTag,
                "Inside OnResume() and title of 1st ele of listOfNewsArticleCat1 is : ${viewModel.listOfNewsArticleCat1[0].title}  "
            )
        }
        if (!isEmptyForCat2) {
            Log.d(
                Constants.currentDebugTag,
                "Inside OnResume() and title of 1st ele of listOfNewsArticleCat2 is : ${viewModel.listOfNewsArticleCat2[0].title}  "
            )
        }
//
        if (category == "india" || category == Constants.category1) {
            var isEmpty = viewModel.listOfNewsArticleCat1.isNullOrEmpty()
            if (isEmpty) {
                Log.d(
                    Constants.currentDebugTag,
                    "inside If listOfNewsArticleCat1 is empty}"
                )
            } else {
                Log.d(
                    Constants.currentDebugTag,
                    "inside If and title of 1st news : ${viewModel.listOfNewsArticleCat1[0].title}"
                )
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat1) // I guess ismei latest Data is store hota hai
                }
            }

        } else if (category == "covid" || category == Constants.category2) {
            var isEmpty = viewModel.listOfNewsArticleCat2.isNullOrEmpty()
            if (isEmpty) {
                Log.d(
                    Constants.currentDebugTag,
                    "inside If listOfNewsArticleCat2 is empty}"
                )
            } else {
                Log.d(
                    Constants.currentDebugTag,
                    "inside else If and title of 1st news : ${viewModel.listOfNewsArticleCat2[0].title}"
                )
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat2) // I guess ismei latest Data is store hota hai
                }
            }

        } else {
            var isEmpty = viewModel.listOfNewsArticle.isNullOrEmpty()
            if (isEmpty) {
                Log.d(
                    Constants.currentDebugTag,
                    "inside If listOfNewsArticle is empty}"
                )
            } else {
                Log.d(
                    Constants.currentDebugTag,
                    "inside else and title of 1st news : ${viewModel.listOfNewsArticle[0].title}"
                )
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticle) // I guess ismei latest Data is store hota hai
                }
            }
        }
////
////            adapter.let {
////                it?.setNews(viewModel.listOfNewsArticle) // I guess ismei latest Data is store hota hai
////            }
////
////        }

        super.onResume()
    }

    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(
            Constants.currentDebugTag,
            "Inside onCreateView() of Articles Frag , Category= $category"
        )

        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE

        setupRecyclerView()

        setupApiViewModel()
        setupCacheViewModel()

        val cat = arguments?.getString("category", "business")
        val categoryFromArgs = cat ?: "business"
        category = viewModel.getCategoryStringForApi(categoryFromArgs)

        callingForNewsArticles()





        return binding.root
    }


    private fun callingForNewsArticles() {
        Log.d(
            Constants.currentDebugTag,
            "In callingForNewsArticles() in Articles Frag and category in : $category"
        )
        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE

        viewModel.getPostForArticlesPage(category)

        viewModel.articlesPageResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d(
                Constants.currentDebugTag,
                "Response in articlesPageResponse livedata observer in AF , StoredCategory is :$category and Response Category is  : ${response.category} and 1st Article Title: ${response.newsReceiver.articles[0].title}"
            )
            var holderObject = response
            if (holderObject.category == "india") {
                Log.d(
                    Constants.currentDebugTag,
                    "Inside if callingForNewsArticles of Articles Frag , inside LiveData observe, Category= $category , adding data : ${response.newsReceiver.articles[0].title} "
                )
                viewModel.listOfNewsArticleCat1 = holderObject.newsReceiver.articles.toMutableList()
            } else if (holderObject.category == "covid") {
                Log.d(
                    Constants.currentDebugTag,
                    "Inside else if callingForNewsArticles of Articles Frag , inside LiveData observe, Category= $category , adding data : ${response.newsReceiver.articles[0].title}"
                )
                viewModel.listOfNewsArticleCat2 = holderObject.newsReceiver.articles.toMutableList()
            } else {
                Log.d(
                    Constants.currentDebugTag,
                    "Inside else callingForNewsArticles of Articles Frag , inside LiveData observe, Category= $category , adding data : ${response.newsReceiver.articles[0].title}"
                )
                viewModel.listOfNewsArticle = holderObject.newsReceiver.articles.toMutableList()
            }


            adapter?.setNews(holderObject.newsReceiver.articles) // idhr recycler view mei data set krna hi pdega cause api response kaafi time baad bhi de skti hai utni der mei onResume execute ho jaega

//            viewModel.listOfNewsArticle = response.articles.toMutableList()
//            viewModel.listOfNewsArticleCat1 = viewModel.listOfNewsArticle

            binding.circularProgressBarArticlesPage.visibility = View.GONE
        })
    }


    private fun setupRecyclerView() {
        layoutManager =
            GridLayoutManager(context, 2) // this 2 is basically number of columns u want
        binding.newsListRecyclerView.layoutManager = layoutManager
        adapter =
            context?.let {
                NewsListRecyclerAdapter(
                    it,
                    this
                )
            } // sending context to adapter so that Glide can use it
        binding.newsListRecyclerView.adapter = adapter
    }

    private fun setupApiViewModel() {
        val viewModelFactory = ArticlesPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            )[ArticlesPageViewModel::class.java]
    }

    private fun setupCacheViewModel() {
        val viewModelCacheFactory = ViewModelForCacheFactory(requireActivity().application)
        viewModelForCache = ViewModelProvider(
            requireActivity(),
            viewModelCacheFactory
        )[ViewModelForCache::class.java]
    }

    override fun onBookmarkButtonClick(position: Int) {
        viewModelForCache.addNewsArticle(
            viewModel.onBookMarkButtonClickedCode(
                viewModel.listOfNewsArticle,
                position
            )
        )
        Toast.makeText(context, "YO Inside On Bookmark $position  END", Toast.LENGTH_SHORT).show()
    }

}