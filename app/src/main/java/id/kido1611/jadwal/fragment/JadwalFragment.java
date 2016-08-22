package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class JadwalFragment extends BaseFragment {

    public JadwalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jadwal, container, false);
    }
}
