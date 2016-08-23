package id.kido1611.jadwal.object;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmad on 21/08/2016.
 */
public class MataKuliah extends RealmObject {

    @PrimaryKey
    private String id;
    private String semester_id;
    private String nama;
    private RealmList<MakulHari> listHari;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(String semester_id) {
        this.semester_id = semester_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public RealmList<MakulHari> getListHari() {
        return listHari;
    }

    public void setListHari(RealmList<MakulHari> listHari) {
        this.listHari = listHari;
    }
}
