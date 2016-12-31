package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.model.ViewPagerAdapter;
import id.kido1611.jadwal.object.MakulHari;
import id.kido1611.jadwal.object.MataKuliah;
import id.kido1611.jadwal.object.Note;

/**
 * Created by ahmad on 31/08/2016.
 */
public class NoteFragment extends BaseFragment {

    public static NoteFragment newInstance(String id){
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String id = getArguments().getString("id");
        if(id.equals("")){
            setAppTitle(getString(R.string.nav_item_note));
        }else{
            //Note noteId = getRealm().where(Note.class).equalTo("makul_hari_id", id).findFirst();
            MakulHari hariId = getRealm().where(MakulHari.class).equalTo("id", id).findFirst();
            MataKuliah makul = getRealm().where(MataKuliah.class).equalTo("id", hariId.getMakul_id()).findFirst();
            setAppTitle(getString(R.string.nav_item_note)+" "+makul.getNama());
        }

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(NoteListFragment.newInstance(id, false), getString(R.string.nav_item_note));
        mAdapter.addFragment(NoteListFragment.newInstance(id, true), getString(R.string.nav_item_arsip));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);
        ButterKnife.bind(this, rootView);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        return rootView;
    }
}
