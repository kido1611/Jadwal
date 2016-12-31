package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.model.HariRecyclerAdapter;
import id.kido1611.jadwal.model.JadwalRecyclerAdapter;
import id.kido1611.jadwal.object.MakulHari;
import id.kido1611.jadwal.object.Semester;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ahmad on 25/08/2016.
 */
public class JadwalHariFragment extends BaseFragment
        implements HariRecyclerAdapter.MakulHariItemListener{

    public static JadwalHariFragment newInstance(String makulid, String hari){
        JadwalHariFragment fragment = new JadwalHariFragment();
        Bundle args = new Bundle();
        args.putString("id", makulid);
        args.putString("hari", hari);
        fragment.setArguments(args);

        return fragment;
    }

    public JadwalHariFragment() {
    }

    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;

    private Semester currentSemester;
    private JadwalRecyclerAdapter mAdapter;
    private RealmResults<MakulHari> hariList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentSemester = getRealm().where(Semester.class).equalTo("id", getArguments().getString("id")).findFirst();
        hariList = getRealm().where(MakulHari.class).equalTo("semester_id", getArguments().getString("id")).equalTo("hari", getArguments().getString("hari")).findAllSorted("jam_awal", Sort.ASCENDING);
        mAdapter = new JadwalRecyclerAdapter(getRealm(), getActivity(),hariList, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jadwal_hari, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onClickListener(int position, MakulHari item) {
        openFragmentWithStack(NoteFragment.newInstance(item.getId()), getString(R.string.nav_item_note));
    }

    @Override
    public void onLongClickListener(int position, MakulHari item) {

    }
}
