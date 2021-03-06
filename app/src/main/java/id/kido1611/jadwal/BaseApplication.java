package id.kido1611.jadwal;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.common.StringUtil;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by ahmad on 21/08/2016.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .migration(new DataMigration())
                .schemaVersion(5)
                .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(Stetho.newInitializerBuilder(this).enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build()).build());
    }

    private class DataMigration implements RealmMigration{

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();
            if(oldVersion==0){
                RealmObjectSchema noteSchema = schema.create("Note")
                        .addField("id", Integer.class, FieldAttribute.PRIMARY_KEY)
                        .addField("title", String.class)
                        .addField("note", String.class)
                        .addField("date", Long.class)
                        .setNullable("date", true);

                oldVersion++;
            }
            if(oldVersion==1){
                RealmObjectSchema hari = schema.get("MakulHari");
                hari.addField("kelas", String.class);

                oldVersion++;
            }
            if(oldVersion==2){
                RealmObjectSchema hari = schema.get("MakulHari");
                hari.addRealmListField("listNote", schema.get("Note"));
                oldVersion++;
            }
            if(oldVersion==3){
                RealmObjectSchema note = schema.get("Note");
                note.addField("makul_hari_id", String.class);
                note.addField("arsip", Boolean.class);
                note.addField("last_edit", Long.class);
                note.renameField("date", "created_date");
                note.addField("temp_id", String.class);
                note.transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        long id = obj.getLong("id");
                        obj.setString("id", String.valueOf(id));
                    }
                })
                        .removeField("id")
                        .renameField("temp_id", "id");
                oldVersion++;
            }
            if(oldVersion==4){
                RealmObjectSchema note = schema.get("Note");
                note.renameField("makul_hari_id", "makul_id");
                oldVersion++;
            }
        }
    }
}
