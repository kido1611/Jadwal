package id.kido1611.jadwal;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import id.kido1611.jadwal.object.MakulHari;
import id.kido1611.jadwal.object.MataKuliah;
import id.kido1611.jadwal.object.Semester;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ahmad on 21/08/2016.
 */
public abstract class BaseFragment extends Fragment {

    public static final String EXTRA_ID = "id";

    public void setAppTitle(String title){
        if(title==null) return;

        if((MainActivity)getActivity()==null) return;

        if(((MainActivity)getActivity()).getSupportActionBar()==null) return;

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(title);
    }

    public void openFragment(Fragment fragment, String tag){
        ((MainActivity)getActivity()).openFragment(fragment, tag);
    }

    public void showSnackBar(String message){
        showSnackBar(message, Snackbar.LENGTH_SHORT, null, null, null);
    }
    public void showSnackBar(String message, int length){
        showSnackBar(message, length, null, null, null);
    }
    public void showSnackBar(String message, int length, Snackbar.Callback callback, String actionTitle, View.OnClickListener clickListener){
        ((MainActivity)getActivity()).showSnackBar(message, length, callback, actionTitle, clickListener);
    }

    public Realm getRealm(){
        return Realm.getDefaultInstance();
    }

    public int getSemesterSize(){
        return getSize(Semester.class);
    }
    public int getMatakuliahSize(){
        return getSize(MataKuliah.class);
    }
    public int getHariSize(){
        return getSize(MakulHari.class);
    }

    /**
     * Get size of @param data.
     * @param classSize Class of RealmObject
     * @return
     */
    public int getSize(Class<? extends RealmObject> classSize){
        int size = getRealm().where(classSize).findAll().size();
        return size;
    }

    public void addData(RealmObject object) {
        this.getRealm().beginTransaction();
        this.getRealm().copyToRealm(object);
        this.getRealm().commitTransaction();
    }

    public void addData(List<RealmObject> listObject) {
        this.getRealm().beginTransaction();
        this.getRealm().copyToRealm(listObject);
        this.getRealm().commitTransaction();
    }

    public void deleteData(Class<? extends RealmObject> cls, String id) {
        this.getRealm().beginTransaction();
        this.getRealm().where(cls.asSubclass(RealmObject.class)).equalTo("id", id).findAll().deleteAllFromRealm();
        this.getRealm().commitTransaction();
    }

    public void updateData(RealmObject object){
        this.getRealm().beginTransaction();
        this.getRealm().copyToRealmOrUpdate(object);
        this.getRealm().commitTransaction();
    }

}
