package lfp.support.ma.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;

/**
 * <pre>
 * Tip:
 *      模块模板，用于生成模块。
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/12 17:22
 * </pre>
 */
public class Module implements ModulePlatformContext, ModulePlatformLifecycle, ModulePlatformApi {
    private final WeakReference<ModulePlatform> mPlatformOwner;

    public Module(ModulePlatform platform) {
        mPlatformOwner = new WeakReference<>(platform);
        platform.addModule(this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if(checkPlatformOwnerIsNull())return;
        mPlatformOwner.get().startActivityForResult(intent, requestCode);
    }

    @Override
    public Context getContext() {if(checkPlatformOwnerIsNull())return null;
        return mPlatformOwner.get().getContext();
    }

    @Override
    public Activity getActivity() {if(checkPlatformOwnerIsNull())return null;
        return mPlatformOwner.get().getActivity();
    }

    @Override
    public FragmentManager getSmartFragmentManager() {if(checkPlatformOwnerIsNull())return null;
        return mPlatformOwner.get().getSmartFragmentManager();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }


    boolean checkPlatformOwnerIsNull() {
        return mPlatformOwner.get() == null;
    }
}
