package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;

/**
 * Created by ahmad on 01/09/2016.
 */
public class ManageDbFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_db, container, false);
        ButterKnife.bind(this, rootView);

        setAppTitle(getString(R.string.nav_item_backup));
        return rootView;
    }
}
