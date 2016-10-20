package com.easycore.nomadesk.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.easycore.nomadesk.R;

public class TagsLayout extends LinearLayout {

    public TagsLayout(Context context) {
        super(context);
        init(context);
    }

    public TagsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TagsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        inflate(context, R.layout.tags_layout, this);
    }
}
