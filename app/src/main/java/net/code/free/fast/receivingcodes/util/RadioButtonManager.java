package net.code.free.fast.receivingcodes.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import net.code.free.fast.receivingcodes.R;

public class RadioButtonManager {
    private static RadioButtonManager i;

    private RadioButtonManager() {}

    public static RadioButtonManager get() {
        if (i == null) {
            synchronized (RadioButtonManager.class) {
                if (i == null) {
                    i = new RadioButtonManager();
                }
            }
        }
        return i;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor", "ResourceType"})
    public RadioButton createRadioButton(Context context, String content) {
        RadioButton rb = new RadioButton(context);
        RadioGroup.LayoutParams p = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                ResourceManager.get().dip2px(context, 50f)
        );
        p.topMargin = ResourceManager.get().dip2px(context, 6f); // 设置上间距为8dp
        rb.setLayoutParams(p);
        rb.setGravity(Gravity.CENTER);
        rb.setText(content);
        rb.setTextSize(14f);
        rb.setButtonDrawable(null);
        rb.setMaxLines(2);
        rb.setEllipsize(TextUtils.TruncateAt.END);

        rb.setPadding(
                ResourceManager.get().dip2px(context, 4f),
                0,
                ResourceManager.get().dip2px(context, 4f),
                0
        );
        Drawable background = ContextCompat.getDrawable(context, R.drawable.country_rb_selector);
        rb.setBackground(background);
        rb.setTextColor(ContextCompat.getColorStateList(context, R.drawable.rb_textcolor));
        return rb;
    }
}