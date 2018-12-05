package support.lfp.ma.base;

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
 *      虚拟平台相关维护
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/5 15:41
 * </pre>
 */
class LifecycleActivity extends AppCompatActivity implements ModulePlatformOwner {

    private ModulePlatform mPlatfrom;
    private PlatformLifecycleObserver mPlatformLifecycleObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlatfrom = new ModulePlatform(this);
        mPlatformLifecycleObserver = new PlatformLifecycleObserver(mPlatfrom);
        getLifecycle().addObserver(mPlatformLifecycleObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mPlatformLifecycleObserver);
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
