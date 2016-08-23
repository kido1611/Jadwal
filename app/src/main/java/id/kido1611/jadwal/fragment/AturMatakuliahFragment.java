package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.model.EditTextWatcher;
import id.kido1611.jadwal.model.MatakuliahRecyclerAdapter;
import id.kido1611.jadwal.object.MataKuliah;
import id.kido1611.jadwal.object.Semester;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ahmad on 22/08/2016.
 */
public class AturMatakuliahFragment extends BaseFragment
        implements MatakuliahRecyclerAdapter.MatakuliahItemListener{

    public static AturMatakuliahFragment newInstance(long semesterId){
        AturMatakuliahFragment fragment = new AturMatakuliahFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, semesterId);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;

    private MatakuliahRecyclerAdapter mAdapter;
    private RealmResults<MataKuliah> resultData;

    @OnClick(R.id.fab_add)
    public void fab_add(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_add_matakuliah)
                .customView(R.layout.dialog_ubah_matakuliah, true)
                .positiveText(R.string.button_add)
                .negativeText(R.string.button_close)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View dialogView = dialog.getCustomView();
                        mEditNama = (TextInputEditText) dialogView.findViewById(R.id.text_input_edit_nama);
                        if(mEditNama.getText().length()>0) {
                            addMatakuliah(dialog.getCustomView());
                            dialog.dismiss();
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

        View view = builder.build().getCustomView();
        mEditNama = (TextInputEditText) view.findViewById(R.id.text_input_edit_nama);
        mInputLayoutNama = (TextInputLayout) view.findViewById(R.id.text_input_layout_nama);
        mEditNama.addTextChangedListener(new EditTextWatcher(mInputLayoutNama, getString(R.string.error_zero_input)));

        builder.build().show();
    }

    TextInputEditText mEditNama;
    TextInputLayout mInputLayoutNama;

    private void addMatakuliah(View view){
        mEditNama = (TextInputEditText) view.findViewById(R.id.text_input_edit_nama);

        MataKuliah makul = new MataKuliah();
        makul.setId(String.valueOf(System.currentTimeMillis()));
        makul.setNama(mEditNama.getText().toString());
        makul.setSemester_id(currentSemester.getId());

        getRealm().beginTransaction();
        currentSemester.getMatakuliahList().add(makul);
        getRealm().commitTransaction();

        showSnackBar(getString(R.string.success_add));
    }

    private Semester currentSemester = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long id = getArguments().getLong(EXTRA_ID);
        currentSemester = getRealm().where(Semester.class).equalTo("id", String.valueOf(id)).findFirst();
        if(currentSemester==null){
            openFragment(AturSemesterFragment.newInstance(false), getString(R.string.title_atur_semester));
        }
        setAppTitle("Semester "+currentSemester.getSemester());

        mAdapter = new MatakuliahRecyclerAdapter(getActivity(), currentSemester.getMatakuliahList(), this);
        currentSemester.addChangeListener(new RealmChangeListener<RealmModel>() {
            @Override
            public void onChange(RealmModel element) {
                mAdapter.notifyDataSetChanged();
            }
        });

        //setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_atur_matakuliah, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        setAppTitle("Semester "+currentSemester.getSemester());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentSemester.removeChangeListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_atur_matakuliah, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showSnackBar(item.getTitle().toString());
//        switch (item.getItemId()){
//            case R.id.action_edit:
//
//                return true;
//            case R.id.action_archieve:
//                getRealm().executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        currentSemester.setArsip(!currentSemester.isArsip());
//
//                        if(currentSemester.isAktif()) {
//                            currentSemester.setAktif(false);
//
//                            RealmResults<Semester> lists = realm.where(Semester.class).equalTo("arsip", false).findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
//                            if(lists.size() > 0){
//                                Semester newAktif = lists.first();
//                                if (newAktif != null) {
//                                    newAktif.setAktif(true);
//                                }
//                            }
//                        }
//                        if(!currentSemester.isArsip()){
//                            isContainAktif(realm);
//                        }
//                        showSnackBar(getString(R.string.success_arsip));
//                    }
//                });
//                return true;
//            case R.id.action_delete:
//                getRealm().executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        boolean itemAktif = currentSemester.isAktif();
//                        realm.where(Semester.class).equalTo("id", currentSemester.getId()).findAll().deleteAllFromRealm();
//
//                        if(itemAktif) {
//                            RealmResults<Semester> lists = realm.where(Semester.class)
//                                    .equalTo("arsip", false)
//                                    .findAllSorted(new String[]{"aktif", "tahun_awal", "semester"}, new Sort[]{Sort.DESCENDING, Sort.DESCENDING, Sort.DESCENDING});
//                            if(lists.size() > 1){
//                                Semester newAktif = lists.first();
//                                if (newAktif != null) {
//                                    newAktif.setAktif(true);
//                                }
//                            }
//                        }
//                        showSnackBar(getString(R.string.success_delete));
//                    }
//                });
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return super.onOptionsItemSelected(item);
    }

    private void editMatakuliah(MataKuliah item, View view){
        mEditNama = (TextInputEditText) view.findViewById(R.id.text_input_edit_nama);

        getRealm().beginTransaction();
        item.setNama(mEditNama.getText().toString());
        getRealm().commitTransaction();

        showSnackBar(getString(R.string.success_edit));
    }
    private void editMatakuliahDialog(final MataKuliah item){
        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.title_edit)+" matakuliah "+item.getNama())
                .positiveText(R.string.button_edit)
                .neutralText(R.string.button_close)
                .customView(R.layout.dialog_ubah_matakuliah, true)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        View dialogView = dialog.getCustomView();
                        mEditNama = (TextInputEditText) dialogView.findViewById(R.id.text_input_edit_nama);
                        if(mEditNama.getText().length()>0) {
                            editMatakuliah(item, dialogView);
                            dialog.dismiss();
                        }
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

        mEditNama = (TextInputEditText) view.findViewById(R.id.text_input_edit_nama);
        mInputLayoutNama = (TextInputLayout) view.findViewById(R.id.text_input_layout_nama);
        mEditNama.addTextChangedListener(new EditTextWatcher(mInputLayoutNama, getString(R.string.error_zero_input)));
        mEditNama.setText(item.getNama());

        dialog.show();
    }

    @Override
    public void onClickListener(int position, MataKuliah item) {
        openFragmentWithStack(AturMatakuliahHariFragment.newInstance(item.getId()), item.getNama());
    }

    @Override
    public void onLongClickListener(int position, final MataKuliah item) {
        new MaterialDialog.Builder(getActivity())
                .title("Matakuliah: "+item.getNama())
                .negativeText(R.string.button_close)
                .items(R.array.arrays_atur_item_matakuliah)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if(position==0)
                            editMatakuliahDialog(item);
                        else if(position==1){
                            getRealm().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.where(MataKuliah.class).equalTo("id", item.getId()).findAll().deleteAllFromRealm();
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
