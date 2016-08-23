package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.object.Semester;

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
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_add_matakuliah)
                //.customView(R.layout.dialog_ubah_matakuliah, true)
                .positiveText(R.string.button_add)
                .negativeText(R.string.button_close)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//
//                    }
//                })
                .input(R.string.hint_input_mata_kuliah, 0, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        showSnackBar(input.toString());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        builder.show();

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
        setAppTitle(getString(R.string.title_arsip)+": "+currentSemester.getSemester());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_atur_matakuliah, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
