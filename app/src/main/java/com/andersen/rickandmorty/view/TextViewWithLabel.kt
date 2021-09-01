package com.andersen.rickandmorty.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue.*
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.andersen.rickandmorty.R

class TextViewWithLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var label = ""
    var text = ""
        set(value) {
            field = value
            tvText.text = value
        }

    private var tvLabel: TextView
    private var tvText: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_text_view_with_label, this)

        tvLabel = findViewById(R.id.tvLabel)
        tvText = findViewById(R.id.tvText)

        val attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TextViewWithLabel)
        label = attributes.getString(R.styleable.TextViewWithLabel_label) ?: ""
        text = attributes.getString(R.styleable.TextViewWithLabel_text) ?: ""
        attributes.recycle()

        tvLabel.text = label
        tvText.text = text
    }
}