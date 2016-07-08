package com.appdever.foody.searchPage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * Created by landtanin on 7/8/2016 AD.
 */
public class DeselectableRadioButton extends CompoundButton {

    public DeselectableRadioButton(Context context) {
        this(context, null);
    }

    public DeselectableRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.radioButtonStyle);
    }

    public DeselectableRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method will toggle the radio button.
     */
    @Override
    public void toggle() {
        super.toggle();
    }
}