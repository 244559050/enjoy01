package com.zero.demo01.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.zero.demo01.R
import com.zero.demo01.domain.commands.RequestDayForecastCommand
import com.zero.demo01.domain.model.Forecast
import com.zero.demo01.extensions.color
import com.zero.demo01.extensions.textColor
import com.zero.demo01.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import java.text.DateFormat

class DetailActivity : CoroutineScopeActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()

        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        launch {
            val id = intent.getLongExtra(ID, -1)
            val result = RequestDayForecastCommand(id).execute()
            bindForecast(result)
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(this@DetailActivity).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    @SuppressLint("SetTextI18n")
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}º"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}