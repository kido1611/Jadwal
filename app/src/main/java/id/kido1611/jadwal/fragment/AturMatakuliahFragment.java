package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;

/**
 * Created by ahmad on 22/08/2016.
 */
public class AturMatakuliahFragment extends BaseFragment {

    public static AturMatakuliahFragment newInstance(long semesterId){
        AturMatakuliahFragment fragment = new AturMatakuliahFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, semesterId);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;

    @OnClick(R.id.fab_add)
    public void fab_add(){
        showSnackBar("mata kuliah");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTitle(getString(R.string.title_atur_matakuliah));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_atur_matakuliah, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
