package id.kido1611.jadwal.object;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ahmad on 28/08/2016.
 */
public class Note extends RealmObject {
    @PrimaryKey
    private String id;
    private String title;
    private String note;
    private long created_date;
    private long last_edit;
    private String makul_id;
    private boolean arsip;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreated_date() {
        return created_date;
    }

    public void setCreated_date(long created_date) {
        this.created_date = created_date;
    }

    public long getLast_edit() {
        return last_edit;
    }

    public void setLast_edit(long last_edit) {
        this.last_edit = last_edit;
    }

    public String getMakul_id() {
        return makul_id;
    }

    public void setMakul_id(String makul_id) {
        this.makul_id = makul_id;
    }

    public boolean isArsip() {
        return arsip;
    }

    public void setArsip(boolean arsip) {
        this.arsip = arsip;
    }
}
