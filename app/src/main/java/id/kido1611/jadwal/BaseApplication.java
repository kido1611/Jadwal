package id.kido1611.jadwal;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.common.StringUtil;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.DynamicRealm;
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
                //.migration(new DataMigration())
                .schemaVersion(1)
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
        }
    }
}
