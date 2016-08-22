package id.kido1611.jadwal;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.kido1611.jadwal.fragment.AturSemesterFragment;
import id.kido1611.jadwal.fragment.JadwalFragment;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Drawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.nav_item_jadwal).withIcon(getIconDrawable(R.drawable.ic_list)),
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.nav_item_atur).withIcon(getIconDrawable(R.drawable.ic_settings)),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.nav_item_arsip).withIcon(getIconDrawable(R.drawable.ic_archive))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        openDisplay(drawerItem);
                        return true;
                    }
                })
                .build();

        mDrawer.setSelection(1, true);
    }

    private void openDisplay(IDrawerItem item){
        long identifier = item.getIdentifier();
        String title = ((Nameable)item).getName().getText(this);

        if(identifier==1){
            openFragment(new JadwalFragment(), title);
        }else if(identifier==2){
            openFragment(new AturSemesterFragment(), getString(R.string.title_atur_semester));
        }else if(identifier==3){
            openFragment(AturSemesterFragment.newInstance(true), getString(R.string.title_arsip));
        }
        mDrawer.closeDrawer();
    }

    public void openFragment(Fragment fragment, String tag){
        //getSupportActionBar().setTitle(tag);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment, tag).commit();
    }

    private Drawable getIconDrawable(int resId){
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setColorFilter(new PorterDuffColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY));
        return drawable;
    }

    public void showSnackBar(String message, int length, Snackbar.Callback callback, String actionTitle, View.OnClickListener clickListener){
        Snackbar.make(coordinatorLayout, message, length)
                .setAction(actionTitle, clickListener)
                .setCallback(callback)
                .show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen())
            mDrawer.closeDrawer();
        else
            super.onBackPressed();
    }
}
