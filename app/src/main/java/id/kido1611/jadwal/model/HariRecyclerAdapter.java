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
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by ahmad on 23/08/2016.
 */
public class HariRecyclerAdapter extends RealmRecyclerViewAdapter<MakulHari, HariRecyclerAdapter.HariViewHolder>{

    LayoutInflater inflater;
    Context mContext;
    MakulHariItemListener listener;

    public HariRecyclerAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<MakulHari> data, MakulHariItemListener listener) {
        super(context, data, true);
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public HariViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_atur_matakuliah, parent, false);
        return new HariViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HariViewHolder holder, final int position) {
        final MakulHari item = getData().get(position);
        holder.mTextTitle.setText(item.getJam_awal()+"-"+item.getJam_akhir());
        holder.mTextKeterangan.setText(item.getKeterangan());
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

    public interface MakulHariItemListener {
        void onClickListener(int position, MakulHari item);
        void onLongClickListener(int position, MakulHari item);
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
