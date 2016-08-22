package id.kido1611.jadwal.object;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmad on 21/08/2016.
 */
public class Semester extends RealmObject {

    @PrimaryKey
    private String id;
    private String semester;
    private String tahun_awal;
    private String tahun_akhir;
    private boolean aktif;
    private boolean arsip;
    private String keterangan;
    private RealmList<MataKuliah> matakuliahList;

    public RealmList<MataKuliah> getMatakuliahList() {
        return matakuliahList;
    }

    public void setMatakuliahList(RealmList<MataKuliah> matakuliahList) {
        this.matakuliahList = matakuliahList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTahun_awal() {
        return tahun_awal;
    }

    public void setTahun_awal(String tahun_awal) {
        this.tahun_awal = tahun_awal;
    }

    public String getTahun_akhir() {
        return tahun_akhir;
    }

    public void setTahun_akhir(String tahun_akhir) {
        this.tahun_akhir = tahun_akhir;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isArsip() {
        return arsip;
    }

    public void setArsip(boolean arsip) {
        this.arsip = arsip;
    }
}
