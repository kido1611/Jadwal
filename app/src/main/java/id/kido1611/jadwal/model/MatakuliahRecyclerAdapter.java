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
import id.kido1611.jadwal.object.MataKuliah;
import id.kido1611.jadwal.object.Semester;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by ahmad on 23/08/2016.
 */
public class MatakuliahRecyclerAdapter extends RealmRecyclerViewAdapter<MataKuliah, MatakuliahRecyclerAdapter.MatakuliahViewHolder>{

    LayoutInflater inflater;
    Context mContext;
    MatakuliahItemListener listener;

    public MatakuliahRecyclerAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<MataKuliah> data,MatakuliahItemListener listener) {
        super(context, data, true);
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public MatakuliahViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_atur_matakuliah, parent, false);
        return new MatakuliahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatakuliahViewHolder holder, final int position) {
        final MataKuliah item = getData().get(position);
        holder.mTextTitle.setText(item.getNama());
        holder.mTextKeterangan.setText("Hari : "+item.getListHari().size());
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

    public interface MatakuliahItemListener {
        void onClickListener(int position, MataKuliah item);
        void onLongClickListener(int position, MataKuliah item);
    }

    class MatakuliahViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_keterangan)
        TextView mTextKeterangan;

        public MatakuliahViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
