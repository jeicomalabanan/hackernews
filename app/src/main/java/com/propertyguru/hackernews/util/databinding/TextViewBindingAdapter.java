package com.propertyguru.hackernews.util.databinding;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.propertyguru.hackernews.data.model.Story;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

public class TextViewBindingAdapter {
    @BindingAdapter(value = {"html"})
    public static void setHtml(final TextView textView, String html) {
        Spanned val = null;
        if (html != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
                val = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
            else
                val = Html.fromHtml(html);
        }

        textView.setText(val);
    }

    @BindingAdapter(value = {"pretty_date"})
    public static void setPrettyDate(final TextView textView, Date date) {
        if (date != null) {
            textView.setText(DateUtils.getRelativeTimeSpanString(date.getTime()));
        } else {
            textView.setText("");
        }
    }

    @BindingAdapter(value = {"number"})
    public static void setNumber(final TextView textView, int num) {
        textView.setText(String.format("%s.", num));
    }

    @BindingAdapter(value = {"pretty_number"})
    public static void setPrettyNumber(final TextView textView, long number) {
        if (number < 1000) {
            String newVal = String.format(Locale.getDefault(), "%d", number);
            textView.setText(newVal);
        } else {
            int exp = (int) (Math.log(number) / Math.log(1000));

            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setDecimalSeparatorAlwaysShown(false);
            decimalFormat.setMaximumFractionDigits(1);

            String newVal = decimalFormat.format(number / Math.pow(1000, exp));
            String suffix = String.valueOf("kMGTPE".charAt(exp - 1));

            textView.setText(String.format(Locale.getDefault(), "%s%s", newVal, suffix));
        }
    }

    @BindingAdapter(value = {"story_sub"})
    public static void setStorySub(final TextView textView, Story story) {
        if (story == null)
            return;

        String text = String.format(
                Locale.getDefault(),
                "%d points by %s  %s",
                story.getScore(),
                story.getBy(),
                DateUtils.getRelativeTimeSpanString(story.getTime().getTime()));
        textView.setText(text);
    }

    @BindingAdapter(value = {"total_comments"})
    public static void setTotalComments(final TextView textView, Story story) {
        if (story != null && story.getDescendants() != 0) {
            String text = String.format(
                    Locale.getDefault(),
                    "%d comments",
                    story.getDescendants());
            textView.setText(text);

            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
