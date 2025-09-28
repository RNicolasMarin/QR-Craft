package com.qrcraft.scan.presentation.scan_result_preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.Grey2
import com.qrcraft.core.presentation.designsystem.OnSurfaceDisabled
import com.qrcraft.scan.domain.QrTypeTextStateDomain

enum class QrTypeTextState(val maxLines: Int, val overflow: TextOverflow, val buttonRes: Int?, val color: Color?){
    NOT_TEXT(Int.MAX_VALUE, TextOverflow.Clip, null, null),
    TEXT_SHORT(6, TextOverflow.Ellipsis, null, null),
    TEXT_TRUNCATED(6, TextOverflow.Ellipsis, R.string.qr_type_text_show_more, Grey2),
    TEXT_COMPLETED(Int.MAX_VALUE, TextOverflow.Clip, R.string.qr_type_text_show_less, OnSurfaceDisabled)
}

fun QrTypeTextState.toDomain(): QrTypeTextStateDomain {
    return when (this) {
        QrTypeTextState.NOT_TEXT -> QrTypeTextStateDomain.NOT_TEXT
        QrTypeTextState.TEXT_SHORT -> QrTypeTextStateDomain.TEXT_SHORT
        QrTypeTextState.TEXT_TRUNCATED -> QrTypeTextStateDomain.TEXT_TRUNCATED
        QrTypeTextState.TEXT_COMPLETED -> QrTypeTextStateDomain.TEXT_COMPLETED
    }
}

fun QrTypeTextStateDomain.toUi(): QrTypeTextState {
    return when (this) {
        QrTypeTextStateDomain.NOT_TEXT -> QrTypeTextState.NOT_TEXT
        QrTypeTextStateDomain.TEXT_SHORT -> QrTypeTextState.TEXT_SHORT
        QrTypeTextStateDomain.TEXT_TRUNCATED -> QrTypeTextState.TEXT_TRUNCATED
        QrTypeTextStateDomain.TEXT_COMPLETED -> QrTypeTextState.TEXT_COMPLETED
    }
}