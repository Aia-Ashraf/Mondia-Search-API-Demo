package com.example.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle

import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aashraf.mondia_search_api_demo.R
import com.aashraf.mondia_search_api_demo.model.mondiaModels.DataModel
import com.example.search.model.BaseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.search.ChapterAdapter.*
import io.reactivex.functions.Predicate
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var TAG: String = "MainActivity"
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ChapterAdapter
    lateinit var viewHolder: ViewHolder
    lateinit var searchView: SearchView
    lateinit var baseModel: BaseModel
    lateinit var dataModel: ArrayList<DataModel>
    private var mCompositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv)
        searchView = findViewById(R.id.searchView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = ChapterAdapter(this, DataModel.getList())
        recyclerView.adapter = adapter
        mCompositeDisposable = CompositeDisposable()
        getAccessToken()
        ObServableSearchView.of(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter(object : Predicate<String> {
                @Throws(Exception::class)
                override fun test(text: String): Boolean {
                    return if (text.isEmpty()) {
                        Log.d(TAG, "isEmpty: $text")

                        false
                    } else {
                        Log.d(TAG, "onQueryTextSubmit: $text")
                        beginSearch(text)
                        true
                    }
                }
            })
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnEach {
            }
            .doOnError {
                Log.d(TAG, "Error")
            }
            .retry()
            .subscribe({
                Log.d(TAG, "subs")
            }, {
                Log.d(TAG, it.toString())
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.searchView)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = "Search View Hint"
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.setIconifiedByDefault(false)
        }
        return true
    }

    fun beginSearch(query: String) {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://staging-gateway.mondiamedia.com/")
            .build()

        viewHolder = ViewHolder(searchView)
        val mondiaAPI = retrofit.create(MondiaSearchAPI::class.java)
        mCompositeDisposable?.add(
            mondiaAPI.getData(
                "Bearer " + baseModel.accessToken,
                query ,
                20
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    fun handleResponse(dataModel: ArrayList<DataModel>) {
        this.dataModel = dataModel
        dataModel?.let { adapter.updateItems(it) }
    }

    fun getTokenResponse(baseModel: BaseModel) {
        this.baseModel = baseModel

    }

    fun handleError(error: Throwable) {
    }


    fun getAccessToken() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://staging-gateway.mondiamedia.com/")
            .build()
        viewHolder = ViewHolder(searchView)
        val getwayToken = retrofit.create(AccessTokenAPI::class.java)
        mCompositeDisposable?.add(
            getwayToken.getAccessToken(
                "application/x-www-form-urlencoded",
                "Ge6c853cf-5593-a196-efdb-e3fd7b881eca"
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::getTokenResponse, this::handleError)
        )
    }
}