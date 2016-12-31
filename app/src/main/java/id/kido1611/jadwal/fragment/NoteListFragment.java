package id.kido1611.jadwal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.kido1611.jadwal.BaseFragment;
import id.kido1611.jadwal.R;

/**
 * Created by ahmad on 06/10/2016.
 */

public class NoteListFragment extends BaseFragment {

    public static final String NOTE_ID = "id";
    public static final String NOTE_ARCHIEVE = "archieve";

    public static NoteListFragment newInstance(String id, boolean isArchieve){
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        args.putString(NOTE_ID, id);
        args.putBoolean(NOTE_ARCHIEVE, isArchieve);
        fragment.setArguments(args);

        return fragment;
    }

    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_tab, container, false);
        ButterKnife.bind(this, rootView);

        boolean isArsip = getArguments().getBoolean(NOTE_ARCHIEVE, false);
        if(isArsip) mFabAdd.setVisibility(View.GONE);

        return rootView;
    }
}
