package id.kido1611.jadwal;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import id.kido1611.jadwal.fragment.ManageDbFragment;
import id.kido1611.jadwal.fragment.NoteFragment;

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
                        new PrimaryDrawerItem().withIdentifier(4).withName(R.string.nav_item_note).withIcon(getIconDrawable(R.drawable.ic_note)),
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.nav_item_atur).withIcon(getIconDrawable(R.drawable.ic_settings)),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.nav_item_arsip).withIcon(getIconDrawable(R.drawable.ic_archive)),
                        new PrimaryDrawerItem().withIdentifier(5).withName(R.string.nav_item_backup).withIcon(getIconDrawable(R.drawable.ic_backup))
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

    public void openDrawerSelection(int identidier){
        mDrawer.setSelection(identidier, true);
    }

    private void openDisplay(IDrawerItem item){
        long identifier = item.getIdentifier();
        String title = ((Nameable)item).getName().getText(this);

        if(identifier==1){
            openFragment(new JadwalFragment(), title);
        }else if(identifier==2){
            openFragment(AturSemesterFragment.newInstance(false, false), getString(R.string.title_atur_semester));
        }else if(identifier==3){
            openFragment(AturSemesterFragment.newInstance(true, false), getString(R.string.title_arsip));
        }else if(identifier==5){
            openFragment(new ManageDbFragment(), getString(R.string.nav_item_backup));
        }else if(identifier==4){
            openFragment(NoteFragment.newInstance(""), getString(R.string.nav_item_note));
        }
        mDrawer.closeDrawer();
    }

    public void openFragment(Fragment fragment, String tag){
        //getSupportActionBar().setTitle(tag);
        for(int i=0;i<getSupportFragmentManager().getBackStackEntryCount();i++) {
            getSupportFragmentManager().popBackStack();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment, tag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void openFragmentWithStack(Fragment fragment, String tag){
        String backStackName = fragment.getClass().getName();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment)
                .addToBackStack(backStackName).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
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

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed = 0;

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen())
            mDrawer.closeDrawer();
        else{
            if(getSupportFragmentManager().getBackStackEntryCount()>0)
                super.onBackPressed();
            else{
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()){
                    super.onBackPressed();
                    return;
                }else{
                    showSnackBar(getString(R.string.prompt_press_back_to_exit), Snackbar.LENGTH_LONG, null, null, null);
                }
                mBackPressed = System.currentTimeMillis();
            }
        }
    }
}
