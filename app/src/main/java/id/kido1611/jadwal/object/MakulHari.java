package id.kido1611.jadwal.object;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmad on 21/08/2016.
 */
public class MakulHari extends RealmObject {

    @PrimaryKey
    private String id;
    private String semester_id;
    private String makul_id;
    private int jam_awal;
    private int jam_akhir;
    private String hari;
    private String kelas;
    private String keterangan;
    private RealmList<Note> listNote;


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

    public String getMakul_id() {
        return makul_id;
    }

    public void setMakul_id(String makul_id) {
        this.makul_id = makul_id;
    }

    public int getJam_awal() {
        return jam_awal;
    }

    public void setJam_awal(int jam_awal) {
        this.jam_awal = jam_awal;
    }

    public int getJam_akhir() {
        return jam_akhir;
    }

    public void setJam_akhir(int jam_akhir) {
        this.jam_akhir = jam_akhir;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public RealmList<Note> getListNote() {
        return listNote;
    }

    public void setListNote(RealmList<Note> listNote) {
        this.listNote = listNote;
    }
}
