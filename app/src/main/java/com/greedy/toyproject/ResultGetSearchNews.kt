package com.greedy.toyproject

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat

data class ResultGetSearchNews(
    var lastBuildDate: String = "",
    var total: Int = 0,
    var start: Int = 0,
    var display: Int = 0,
    var items: List<Items>
)

data class Items(
    var title: String = "",
    var originallink: String = "",
    var link: String = "",
    var description: String = "",
    var pubDate: String = ""
) {
    val descriptionWithoutHTML: String
        @RequiresApi(Build.VERSION_CODES.N)
        get() = Html.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    val titleWithoutHTML: String
        @RequiresApi(Build.VERSION_CODES.N)
        get() = Html.fromHtml(title, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}