package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.MainActivity;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.model.ViewPagerAdapter;
import id.kido1611.jadwal.object.Semester;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A placeholder fragment containing a simple view.
 */
public class JadwalFragment extends BaseFragment {

    public JadwalFragment() {
    }

    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @OnClick(R.id.fab_add)
    public void fab_click(){
        if(currentAktifSemester==null)
            openFragmentWithStack(AturSemesterFragment.newInstance(false, true), getString(R.string.title_atur_semester));
        else
            openFragmentWithStack(AturMatakuliahFragment.newInstance(Long.valueOf(currentAktifSemester.getId())), "MatakuliahFragment");
    }

    private ViewPagerAdapter mAdapter;
    private Semester currentAktifSemester = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RealmResults<Semester> listAll =getRealm().where(Semester.class).equalTo("arsip", false).equalTo("aktif", true).findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});

        if(listAll.size()==0){
            ((MainActivity)getActivity()).openDrawerSelection(2);
            //openFragment(AturSemesterFragment.newInstance(false, true), getString(R.string.title_arsip));
        }else
            currentAktifSemester = listAll.first();

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        if(currentAktifSemester!=null) {
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_senin)), getString(R.string.spinner_hari_senin));
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_selasa)), getString(R.string.spinner_hari_selasa));
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_rabu)), getString(R.string.spinner_hari_rabu));
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_kamis)), getString(R.string.spinner_hari_kamis));
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_jumat)), getString(R.string.spinner_hari_jumat));
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_sabtu)), getString(R.string.spinner_hari_sabtu));
            mAdapter.addFragment(JadwalHariFragment.newInstance(currentAktifSemester.getId(), getString(R.string.spinner_hari_minggu)), getString(R.string.spinner_hari_minggu));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_jadwal, container, false);
        ButterKnife.bind(this, rootView);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);

        if(currentAktifSemester!=null)
            setAppTitle(getString(R.string.app_name)+" semester "+currentAktifSemester.getSemester());
        return rootView;
    }
}
