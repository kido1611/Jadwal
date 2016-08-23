package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.object.MataKuliah;

/**
 * Created by ahmad on 23/08/2016.
 */
public class AturMatakuliahHariFragment extends BaseFragment {

    public static AturMatakuliahHariFragment newInstance(String matakuliahId){
        AturMatakuliahHariFragment fragment = new AturMatakuliahHariFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_ID, matakuliahId);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_add)
    FloatingActionButton mFab_add;

    private MataKuliah currentMatakuliah;

    @OnClick(R.id.fab_add)
    public void fab_click(){
        showSnackBar("Fab click");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(EXTRA_ID);
        currentMatakuliah = getRealm().where(MataKuliah.class).equalTo("id", id).findFirst();
        if(currentMatakuliah==null){
            openFragment(AturSemesterFragment.newInstance(false), getString(R.string.title_atur_semester));
        }
        setAppTitle(currentMatakuliah.getNama());

//        mAdapter = new MatakuliahRecyclerAdapter(getActivity(), currentSemester.getMatakuliahList(), this);
//        currentSemester.addChangeListener(new RealmChangeListener<RealmModel>() {
//            @Override
//            public void onChange(RealmModel element) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_atur_hari, container, false);
        ButterKnife.bind(this, rootView);

        setAppTitle(currentMatakuliah.getNama());

        return rootView;
    }
}
