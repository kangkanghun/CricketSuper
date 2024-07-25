package com.moingay.cricketsuper.utils

import android.content.Context
import android.util.Log
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.moingay.cricketsuper.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

inline fun <T> toResultFlow(
    context: Context,
    crossinline call: suspend () -> Response<T>
): Flow<NetWorkResult<T>> {
    return flow {
        if (Constants.hasInternetConnection(context)) {
            emit(NetWorkResult.Loading(true))
            try {
                val response = call()
                if (response.isSuccessful && response.body() != null) {
                    emit(NetWorkResult.Success(response.body()))
                } else {
                    emit(NetWorkResult.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(NetWorkResult.Error(e.toString()))
            }
        } else {
            emit(NetWorkResult.Error(context.getString(R.string.label_no_internet)))
        }
    }.flowOn(Dispatchers.IO)
}


fun DrawScope.drawRhombus(width: Float, height: Float, color: Color, cornerRadius: Float) {
    val halfWidth = width / 2
    val halfHeight = height / 2
    val path = Path().apply {
        moveTo(halfWidth, 0f)
        cubicTo(
            halfWidth + cornerRadius, 0f,
            width, halfHeight - cornerRadius,
            width, halfHeight
        )
        cubicTo(
            width, halfHeight + cornerRadius,
            halfWidth + cornerRadius, height,
            halfWidth, height
        )
        cubicTo(
            halfWidth - cornerRadius, height,
            0f, halfHeight + cornerRadius,
            0f, halfHeight
        )
        cubicTo(
            0f, halfHeight - cornerRadius,
            halfWidth - cornerRadius, 0f,
            halfWidth, 0f
        )
        close()
    }
    drawPath(path, color)
}