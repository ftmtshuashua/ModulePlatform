package lfp.support.ma.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * <pre>
 * Tip:
 *      Activity 模块化平台
 *
 * Function:
 *
 *
 *
 * Created by LiFuPing on 2018/11/12 17:16
 * </pre>
 */
public class MPActivity extends AppCompatActivity implements ModulePlatformOwner {

    private ModulePlatform mPlatfrom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlatfrom = new ModulePlatform(this);
        getLifecycle().addObserver(new PlatformLifecycleObserver(mPlatfrom));

    }

    @Override
    public ModulePlatform getPlatform() {
        return mPlatfrom;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPlatfrom.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPlatfrom.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPlatfrom.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPlatfrom.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}


