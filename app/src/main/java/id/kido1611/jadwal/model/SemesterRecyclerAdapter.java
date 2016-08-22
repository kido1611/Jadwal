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
import id.kido1611.jadwal.object.Semester;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by ahmad on 22/08/2016.
 */
public class SemesterRecyclerAdapter extends RealmRecyclerViewAdapter<Semester, SemesterRecyclerAdapter.SemesterViewHolder>{

    LayoutInflater inflater;
    Context mContext;
    SemesterItemListener listener;

    public SemesterRecyclerAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Semester> data, SemesterItemListener listener) {
        super(context, data, true);
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public void setData(RealmResults<Semester> data){
        setData(data);
    }

    @Override
    public SemesterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_row_atur_semester, parent, false);
        SemesterViewHolder holder = new SemesterViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(SemesterViewHolder holder, final int position) {
        final Semester item = getData().get(position);
        holder.mTextTitle.setText("Semester: "+item.getSemester()+" ("+item.getTahun_awal()+"/"+item.getTahun_akhir()+")");
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

    public interface SemesterItemListener {
        void onClickListener(int position, Semester item);
        void onLongClickListener(int position, Semester item);
    }

    class SemesterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_keterangan)
        TextView mTextKeterangan;

        public SemesterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
