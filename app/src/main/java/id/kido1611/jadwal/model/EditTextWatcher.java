package id.kido1611.jadwal.model;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ahmad on 23/08/2016.
 */
public class EditTextWatcher implements TextWatcher {

    TextInputLayout mInputLayout;
    String error;

    public EditTextWatcher(TextInputLayout inputLayout, String error){
        mInputLayout = inputLayout;
        this.error = error;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(editable.length()==0){
            mInputLayout.setError(error);
        }else
            mInputLayout.setErrorEnabled(false);
    }
}
