package id.kido1611.jadwal.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.kido1611.jadwal.R;
import id.kido1611.jadwal.object.MakulHari;
import id.kido1611.jadwal.object.MataKuliah;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by ahmad on 23/08/2016.
 */
public class JadwalRecyclerAdapter extends RealmRecyclerViewAdapter<MakulHari, JadwalRecyclerAdapter.HariViewHolder>{

    LayoutInflater inflater;
    Context mContext;
    HariRecyclerAdapter.MakulHariItemListener listener;
    Realm mRealm;

    public JadwalRecyclerAdapter(Realm realm, @NonNull Context context, @Nullable OrderedRealmCollection<MakulHari> data, HariRecyclerAdapter.MakulHariItemListener listener) {
        super(context, data, true);
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
        mRealm = realm;
    }

    @Override
    public HariViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_atur_matakuliah, parent, false);
        return new HariViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HariViewHolder holder, final int position) {
        final MakulHari item = getData().get(position);
        MataKuliah makul = mRealm.where(MataKuliah.class).equalTo("id", item.getMakul_id()).findFirst();

        String title = null;
        if(makul==null) title = "Matakuliah tidak ada";
        else{
            title = makul.getNama();
            if(item.getKeterangan()!=null && !item.getKeterangan().isEmpty() && !item.getKeterangan().equals(" ") && !item.getKeterangan().equals(""))
                title = title+" ("+item.getKeterangan()+")";
        }


        holder.mTextTitle.setText(title);
        holder.mTextKeterangan.setText(item.getJam_awal()+"-"+item.getJam_akhir());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListener(position, item);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClickListener(position, item);
                return true;
            }
        });
    }

    class HariViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_keterangan)
        TextView mTextKeterangan;

        public HariViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
