package id.kido1611.jadwal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import id.kido1611.jadwal.model.EditTextWatcher;
import id.kido1611.jadwal.model.SemesterRecyclerAdapter;
import id.kido1611.jadwal.object.Semester;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ahmad on 22/08/2016.
 */
public class AturSemesterFragment extends BaseFragment
        implements SemesterRecyclerAdapter.SemesterItemListener{

    public static AturSemesterFragment newInstance(boolean isArsip, boolean newSemester){
        AturSemesterFragment fragment = new AturSemesterFragment();
        Bundle args = new Bundle();
        args.putBoolean("arsip", isArsip);
        args.putBoolean("baru", newSemester);
        fragment.setArguments(args);

        return fragment;
    }
    public static AturSemesterFragment newInstance(Bundle args){
        AturSemesterFragment fragment = new AturSemesterFragment();
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

    private TextInputEditText mEditSemester, mEditTahunAwal, mEditTahunAkhir, mEditKeterangan;
    private TextInputLayout mInputLayoutSemester, mInputLayoutTahunAwal, mInputLayoutTahunAkhir, mInputLayoutKeterangan;
    private CheckBox mCheckBoxAktif;

    @OnClick(R.id.fab_add)
    public void fab_click(){
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_add_semester)
                .positiveText(R.string.button_add)
                .neutralText(R.string.button_close)
                .customView(R.layout.dialog_ubah_semester, true)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
                        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
                        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
                        if(mEditSemester.getText().length()==0) return;
                        if(mEditTahunAwal.getText().length()==0) return;
                        if(mEditTahunAkhir.getText().length()==0) return;
                        addSemester(view);
                        dialog.dismiss();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        View view = dialog.getCustomView();
        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
        mEditKeterangan = (TextInputEditText) view.findViewById(R.id.text_input_edit_keterangan);
        mInputLayoutSemester = (TextInputLayout) view.findViewById(R.id.text_input_layout_semester);
        mInputLayoutTahunAwal = (TextInputLayout) view.findViewById(R.id.text_input_layout_tahun_awal);
        mInputLayoutTahunAkhir = (TextInputLayout) view.findViewById(R.id.text_input_layout_tahun_akhir);
        mInputLayoutKeterangan = (TextInputLayout) view.findViewById(R.id.text_input_layout_keterangan);

        String error_zero = getString(R.string.error_zero_input);
        mEditSemester.addTextChangedListener(new EditTextWatcher(mInputLayoutSemester, error_zero));
        mEditTahunAwal.addTextChangedListener(new EditTextWatcher(mInputLayoutTahunAwal, error_zero));
        mEditTahunAkhir.addTextChangedListener(new EditTextWatcher(mInputLayoutTahunAkhir, error_zero));

        dialog.show();

    }

    public RealmResults<Semester> getSemester(){
//        RealmResults<Semester> results = getRealm().where(Semester.class).equalTo("arsip", isArsip).findAllSortedAsync("tahun_awal", Sort.DESCENDING, "semester", Sort.DESCENDING);
        RealmResults<Semester> results = getRealm().where(Semester.class).equalTo("arsip", isArsip).findAllSortedAsync(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
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

        setAppTitle(getString(isArsip?R.string.title_arsip:R.string.title_atur_semester));

        if(getArguments().getBoolean("baru")) fab_click();

        return rootView;
    }

    private void addSemester(View view){

        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
        mEditKeterangan = (TextInputEditText) view.findViewById(R.id.text_input_edit_keterangan);
        mCheckBoxAktif = (CheckBox) view.findViewById(R.id.checkbox_aktif);

        final Semester semester = new Semester();
        semester.setId(String.valueOf(System.currentTimeMillis()));
        semester.setArsip(false);
        semester.setAktif(mCheckBoxAktif.isChecked());
        semester.setKeterangan(mEditKeterangan.getText().toString());
        semester.setSemester(mEditSemester.getText().toString());
        semester.setTahun_awal(mEditTahunAwal.getText().toString());
        semester.setTahun_akhir(mEditTahunAkhir.getText().toString());

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(semester.isAktif()) {
                    Semester itemAktif = realm.where(Semester.class).equalTo("aktif", true).findFirst();
                    if (itemAktif != null) {
                        itemAktif.setAktif(false);
                    }
                }
                realm.copyToRealm(semester);
                showSnackBar(getString(R.string.success_add));
                cekAktif(false);
            }
        });
    }

    private void cekAktif(boolean openTransaction){
        if(openTransaction) getRealm().beginTransaction();

        int count = getRealm().where(Semester.class).equalTo("arsip", false).equalTo("aktif", true).findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING}).size();
        if(count!=0){
            return;
        }
        Semester currentAktifSemester = getRealm().where(Semester.class).equalTo("arsip", false).equalTo("aktif", false).findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING}).first();
        currentAktifSemester.setAktif(true);

        if(openTransaction) getRealm().commitTransaction();
    }

    @Override
    public void onClickListener(int position, Semester item) {
        openFragmentWithStack(AturMatakuliahFragment.newInstance(Long.parseLong(item.getId())), "MataKuliahFragment");
    }

    @Override
    public void onLongClickListener(int position, final Semester item) {
        new MaterialDialog.Builder(getActivity())
                .title("Semester: "+item.getSemester())
                .negativeText(R.string.button_close)
                .items(item.isArsip()?R.array.arrays_arsip_item:item.isAktif()?R.array.arrays_atur_item:R.array.arrays_atur_item_semester)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if(position==0){
                            editSemesterDialog(item);
                        }if(position==1){
                            getRealm().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    boolean itemAktif = item.isAktif();
                                    realm.where(Semester.class).equalTo("id", item.getId()).findAll().deleteAllFromRealm();

                                    if(itemAktif) {
                                        RealmResults<Semester> lists = realm.where(Semester.class)
                                                .equalTo("arsip", false)
                                                .findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
                                        if(lists.size() > 1){
                                            Semester newAktif = lists.first();
                                            if (newAktif != null) {
                                                newAktif.setAktif(true);
                                            }
                                        }
                                    }
                                    showSnackBar(getString(R.string.success_delete));
                                }
                            });
                        }else if(position==2){
                            getRealm().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    item.setArsip(!item.isArsip());

                                    if(item.isAktif()) {
                                        item.setAktif(false);

                                        RealmResults<Semester> lists = realm.where(Semester.class).equalTo("arsip", isArsip).findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
                                        if(lists.size() > 0){
                                            Semester newAktif = lists.first();
                                            if (newAktif != null) {
                                                newAktif.setAktif(true);
                                            }
                                        }
                                    }
                                    if(!item.isArsip()){
                                        cekAktif(false);
                                    }
                                    showSnackBar(getString(R.string.success_arsip));
                                }
                            });
                        }else if(position==3){
                            getRealm().beginTransaction();
                            Semester itemAktif = getRealm().where(Semester.class).equalTo("aktif", true).findFirst();
                            if(itemAktif!=null){
                                itemAktif.setAktif(false);
                            }
                            item.setAktif(true);
                            getRealm().commitTransaction();
                            showSnackBar(getString(R.string.success_aktif));
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

    private void editSemester(final Semester semester, View view){
        TextInputEditText mEditSemester, mEditTahunAwal, mEditTahunAkhir, mEditKeterangan;
        CheckBox mCheckBoxAktif;

        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
        mEditKeterangan = (TextInputEditText) view.findViewById(R.id.text_input_edit_keterangan);
        mCheckBoxAktif = (CheckBox) view.findViewById(R.id.checkbox_aktif);

        getRealm().beginTransaction();

        if(!mCheckBoxAktif.isChecked()){
            cekAktif(false);
        }else{
            Semester itemAktif = getRealm().where(Semester.class).equalTo("aktif", true).findFirst();
            if (itemAktif != null) {
                itemAktif.setAktif(false);
            }
        }
        semester.setAktif(mCheckBoxAktif.isChecked());
        semester.setKeterangan(mEditKeterangan.getText().toString());
        semester.setSemester(mEditSemester.getText().toString());
        semester.setTahun_awal(mEditTahunAwal.getText().toString());
        semester.setTahun_akhir(mEditTahunAkhir.getText().toString());

        getRealm().commitTransaction();

        showSnackBar(getString(R.string.success_edit));

    }
    private void editSemesterDialog(final Semester item){
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.title_edit)+" Semester "+item.getSemester())
                .positiveText(R.string.button_edit)
                .neutralText(R.string.button_close)
                .customView(R.layout.dialog_ubah_semester, true)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View view = dialog.getCustomView();
                        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
                        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
                        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
                        if(mEditSemester.getText().length()==0) return;
                        if(mEditTahunAwal.getText().length()==0) return;
                        if(mEditTahunAkhir.getText().length()==0) return;
                        editSemester(item, view);
                        dialog.dismiss();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = mBuilder.build();
        View view = dialog.getCustomView();

        mEditSemester = (TextInputEditText) view.findViewById(R.id.text_input_edit_semester);
        mEditTahunAwal = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_awal);
        mEditTahunAkhir = (TextInputEditText) view.findViewById(R.id.text_input_edit_tahun_akhir);
        mEditKeterangan = (TextInputEditText) view.findViewById(R.id.text_input_edit_keterangan);
        mCheckBoxAktif = (CheckBox) view.findViewById(R.id.checkbox_aktif);

        mInputLayoutSemester = (TextInputLayout) view.findViewById(R.id.text_input_layout_semester);
        mInputLayoutTahunAwal = (TextInputLayout) view.findViewById(R.id.text_input_layout_tahun_awal);
        mInputLayoutTahunAkhir = (TextInputLayout) view.findViewById(R.id.text_input_layout_tahun_akhir);
        mInputLayoutKeterangan = (TextInputLayout) view.findViewById(R.id.text_input_layout_keterangan);

        String error_zero = getString(R.string.error_zero_input);
        mEditSemester.addTextChangedListener(new EditTextWatcher(mInputLayoutSemester, error_zero));
        mEditTahunAwal.addTextChangedListener(new EditTextWatcher(mInputLayoutTahunAwal, error_zero));
        mEditTahunAkhir.addTextChangedListener(new EditTextWatcher(mInputLayoutTahunAkhir, error_zero));

        mEditSemester.setText(item.getSemester());
        mEditTahunAwal.setText(item.getTahun_awal());
        mEditTahunAkhir.setText(item.getTahun_akhir());
        mEditKeterangan.setText(item.getKeterangan());
        mCheckBoxAktif.setChecked(item.isAktif());

        if(item.isArsip()) mCheckBoxAktif.setVisibility(View.GONE);

        dialog.show();
    }
}
