package id.kido1611.jadwal.object;

/**
 * Created by ahmad on 25/08/2016.
 */
public class Jadwal {
    private String makul;
    private int jam_awal;
    private int jam_akhir;
    private String hari;
    private String keterangan;

    public String getMakul() {
        return makul;
    }

    public void setMakul(String makul) {
        this.makul = makul;
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
}
