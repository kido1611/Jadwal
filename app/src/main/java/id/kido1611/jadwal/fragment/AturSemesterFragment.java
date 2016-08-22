package id.kido1611.jadwal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.model.SemesterRecyclerAdapter;
import id.kido1611.jadwal.object.Semester;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ahmad on 22/08/2016.
 */
public class AturSemesterFragment extends BaseFragment
        implements SemesterRecyclerAdapter.SemesterItemListener{

    public static AturSemesterFragment newInstance(boolean isArsip){
        AturSemesterFragment fragment = new AturSemesterFragment();
        Bundle args = new Bundle();
        args.putBoolean("arsip", isArsip);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.recycler_list)
    RecyclerView mListView;

    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;

    private SemesterRecyclerAdapter mAdapter;
    private RealmResults<Semester> resultData;

    private boolean isArsip = false;

    @OnClick(R.id.fab_add)
    public void fab_click(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_add_semester)
                .positiveText(R.string.button_add)
                .neutralText(R.string.button_close)
                .customView(R.layout.dialog_ubah_semester, true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View dialogView = dialog.getCustomView();
                        addSemester(dialogView);
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public RealmResults<Semester> getSemester(){
        RealmResults<Semester> results = getRealm().where(Semester.class).equalTo("arsip", isArsip).findAllSortedAsync("id", Sort.DESCENDING);
        return results;
    }

    private void loadData(){
        resultData = getSemester();
        resultData.addChangeListener(new RealmChangeListener<RealmResults<Semester>>() {
            @Override
            public void onChange(RealmResults<Semester> element) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
            isArsip = getArguments().getBoolean("arsip");

        setAppTitle(getString(isArsip?R.string.title_arsip:R.string.title_atur_semester));
        loadData();
        mAdapter = new SemesterRecyclerAdapter(getActivity(), resultData, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resultData.removeChangeListeners();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_atur_semester, container, false);
        ButterKnife.bind(this, rootView);

        if(isArsip) mFabAdd.setVisibility(View.GONE);

        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(mAdapter);
        mListView.setHasFixedSize(true);

        return rootView;
    }

    private void addSemester(View view){
        TextInputEditText mEditSemester, mEditTahunAwal, mEditTahunAkhir, mEditKeterangan;
        CheckBox mCheckBoxAktif;

        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
        mEditKeterangan = (TextInputEditText) view.findViewById(R.id.text_input_edit_keterangan);
        mCheckBoxAktif = (CheckBox) view.findViewById(R.id.checkbox_aktif);

        Semester semester = new Semester();
        semester.setId(String.valueOf(System.currentTimeMillis()));
        semester.setArsip(false);
        semester.setAktif(mCheckBoxAktif.isChecked());
        semester.setKeterangan(mEditKeterangan.getText().toString());
        semester.setSemester(mEditSemester.getText().toString());
        semester.setTahun_awal(mEditTahunAwal.getText().toString());
        semester.setTahun_akhir(mEditTahunAkhir.getText().toString());

        addData(semester);

        mAdapter.notifyDataSetChanged();
        showSnackBar(getString(R.string.success_add));
    }

    @Override
    public void onClickListener(int position, Semester item) {
        openFragment(AturMatakuliahFragment.newInstance(Long.parseLong(item.getId())), "MataKuliahFragment");
    }

    @Override
    public void onLongClickListener(int position, final Semester item) {
        new MaterialDialog.Builder(getActivity())
                .title("Semester: "+item.getSemester())
                .negativeText(R.string.button_close)
                .items(item.isArsip()?R.array.arrays_atur_item:item.isAktif()?R.array.arrays_atur_item:R.array.arrays_atur_item_semester)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if(position==1){
                            deleteData(Semester.class, item.getId());
                            showSnackBar(getString(R.string.success_delete));
                        }else if(position==2){
                            getRealm().beginTransaction();
                            item.setArsip(true);
                            item.setAktif(false);
                            getRealm().commitTransaction();
                            showSnackBar(getString(R.string.success_arsip));
                        }
                        //showSnackBar("pos: "+position+" "+text.toString());
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
