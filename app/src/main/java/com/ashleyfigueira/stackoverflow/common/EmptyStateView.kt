package com.ashleyfigueira.stackoverflow.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.ashleyfigueira.stackoverflow.R
import kotlinx.android.synthetic.main.view_empty_state.view.*

class EmptyStateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_empty_state, this, true)
        empty_state_body.text = context.getString(R.string.empty_view_message)
    }

    fun setBodyText(@StringRes bodyText: Int) {
        empty_state_body.text = context.getString(bodyText)
    }
}