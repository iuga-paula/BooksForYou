package com.android.example.booksforyou.views

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView


class ExpandableHeightGridView : GridView {
    private var isExpanded = true

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(
        context: Context?, attrs: AttributeSet?, defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isExpanded) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            val expandSpec = MeasureSpec.makeMeasureSpec(
                MEASURED_SIZE_MASK, MeasureSpec.AT_MOST
            )
            super.onMeasure(widthMeasureSpec, expandSpec)
            val params = layoutParams
            params.height = measuredHeight
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}