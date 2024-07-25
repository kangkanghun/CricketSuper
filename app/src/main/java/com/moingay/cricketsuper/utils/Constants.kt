package com.moingay.cricketsuper.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

object Constants {
    object MatchAPI {
        const val NAME = "MatchAPI"
        const val URL: String = "https://crichype.updatetechltd.com/score/"
        const val PER_PAGE: Int = 10
        const val YEAR_MONTH: String = "2024-06"
        const val BEARER_TOKEN: String =
            "dab123b990b516da6caaa4b928b8d3485437cc58ca95c7eea03135a4a3d216722010e7140d6c3eb5b2dbde0db3f316e94083e18d43e8e84e822c68be5839ae289ed3c9e228aa48d47c6ca61219df43fb5cf6a676ffced72bae980794ba874ab40a0412d77abb7eef1fc1be72b4ddf2e39fbb82fc11880986ff7b32c13cf0f183"
    }

    object SportAPI {
        const val NAME = "SportAPI"
        const val URL: String = "https://sportapi.updatetechltd.com/api/"
        const val BASE_URL: String = "https://sportapi.updatetechltd.com"
        const val TYPE_SORT: String = "id:desc"
        const val POPULATE: String = "*"
    }

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun formatRegexTimeToRegex(
        regexTimeInput: String,
        dateTime: String?,
        regexTime: String
    ): String {
        val inputFormat = SimpleDateFormat(regexTimeInput, Locale.getDefault())
        val outputFormat = SimpleDateFormat(regexTime, Locale.getDefault())

        val date = dateTime?.let {
            try {
                inputFormat.parse(it)
            } catch (_: Exception) {
                null
            }
        }
        return date?.let { outputFormat.format(it) } ?: ""
    }

    fun getBackgroundColorScore(text: String): Color {
        return when {
            text == "w" -> Color.Red.copy(0.8f)
            text.toIntOrNull()
                ?.let { it in 0..3 } == true -> Color.Blue.copy(
                0.8f
            )

            text.toIntOrNull()
                ?.let { it >= 4 } == true -> Color.Green.copy(
                0.8f
            )

            else -> Color.Transparent // Default color if no condition is met
        }
    }

    fun readPrivacyText(context: Context): String {
        return try {
            val inputStream = context.assets.open("privacy.txt")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}