package com.zero.demo01.ui.activities

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.zero.demo01.R
import com.zero.demo01.domain.commands.RequestForecastCommand
import com.zero.demo01.extensions.DelegatesExt
import com.zero.demo01.ui.adapters.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : CoroutineScopeActivity() , ToolbarManager{

    private val zipCode: Long by DelegatesExt.preference(
        this, SettingsActivity.ZIP_CODE,
        SettingsActivity.DEFAULT_ZIP
    )
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = launch {
        val result = RequestForecastCommand(zipCode).execute()
        val adapter = ForecastListAdapter(result) {
            startActivity<DetailActivity>(
                DetailActivity.ID to it.id,
                DetailActivity.CITY_NAME to result.city
            )
        }
        forecastList.adapter = adapter
        toolbarTitle = "${result.city} (${result.country})"
    }
}
