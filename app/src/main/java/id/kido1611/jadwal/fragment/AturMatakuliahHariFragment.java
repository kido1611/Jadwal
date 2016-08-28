package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.model.HariRecyclerAdapter;
import id.kido1611.jadwal.object.MakulHari;
import id.kido1611.jadwal.object.MataKuliah;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;

/**
 * Created by ahmad on 23/08/2016.
 */
public class AturMatakuliahHariFragment extends BaseFragment
        implements HariRecyclerAdapter.MakulHariItemListener{

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

    View dialogView = null;

    private MataKuliah currentMatakuliah;
    private HariRecyclerAdapter mAdapter;

    @OnClick(R.id.fab_add)
    public void fab_click(){
        initDialogView();
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_add_matakuliah_hari)
                .positiveText(R.string.button_add)
                .negativeText(R.string.button_close)
                .customView(dialogView, true)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        addHari(dialog);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });


        builder.build().show();
    }

    private void addHari(MaterialDialog dialog){
        final MakulHari item = new MakulHari();
        item.setId(String.valueOf(System.currentTimeMillis()));
        item.setSemester_id(currentMatakuliah.getSemester_id());
        item.setMakul_id(currentMatakuliah.getId());
        item.setHari(mSpinnerHari.getSelectedItem().toString());
        item.setJam_awal(Integer.parseInt(mSpinnerJamAwal.getSelectedItem().toString()));
        item.setJam_akhir(Integer.parseInt(mSpinnerJamAkhir.getSelectedItem().toString()));
        item.setKeterangan(mInputEditKeterangan.getText().toString());

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                currentMatakuliah.getListHari().add(item);
            }
        });
        showSnackBar(getString(R.string.success_add));
        dialog.dismiss();
    }

    private Spinner mSpinnerHari, mSpinnerJamAwal, mSpinnerJamAkhir;
    private TextInputLayout mInputLayoutKeterangan;
    private TextInputEditText mInputEditKeterangan;

    private void initDialogView(){
        if(dialogView!=null) return;
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_ubah_matakuliah_hari, null, false);

        mInputEditKeterangan = (TextInputEditText) dialogView.findViewById(R.id.text_input_edit_keterangan);
        mInputLayoutKeterangan = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_keterangan);

        mSpinnerHari = (Spinner) dialogView.findViewById(R.id.spinner_hari);
        mSpinnerJamAwal = (Spinner) dialogView.findViewById(R.id.spinner_jam_awal);
        mSpinnerJamAwal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                initSpinnerJam(mSpinnerJamAkhir, i+2, 24);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinnerJamAkhir = (Spinner) dialogView.findViewById(R.id.spinner_jam_akhir);
        initSpinnerJam(mSpinnerJamAwal, 1, 23);
    }

    private void initSpinnerJam(Spinner spinner, int jamAwal, int jamAkhir){
        List<String> listJam = new ArrayList<String>();
        for(int i=jamAwal; i<=jamAkhir;i++){
            listJam.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listJam);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(EXTRA_ID);
        currentMatakuliah = getRealm().where(MataKuliah.class).equalTo("id", id).findFirst();
        if(currentMatakuliah==null){
            openFragment(AturSemesterFragment.newInstance(false, false), getString(R.string.title_atur_semester));
        }
        setAppTitle(currentMatakuliah.getNama());

        mAdapter = new HariRecyclerAdapter(getActivity(), currentMatakuliah.getListHari(), this);
        currentMatakuliah.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_atur_hari, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        setAppTitle(currentMatakuliah.getNama());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentMatakuliah.removeChangeListeners();
    }

    @Override
    public void onClickListener(int position, MakulHari item) {

    }

    @Override
    public void onLongClickListener(int position, final MakulHari item) {
        new MaterialDialog.Builder(getActivity())
                .title("Matakuliah: "+currentMatakuliah.getNama())
                .negativeText(R.string.button_close)
                .items(R.array.arrays_atur_item_matakuliah)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if(position==0) {
                            //editMatakuliahDialog(item);
                        }else if(position==1){
                            getRealm().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.where(MakulHari.class).equalTo("id", item.getId()).findAll().deleteAllFromRealm();
                                    showSnackBar(getString(R.string.success_delete));
                                }
                            });
                        }
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
