package support.lfp.ma.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private WeakReference<ModulePlatform> mPlatformOwner;

    public Module(ModulePlatform platform) {
        setPlatform(platform);
    }

    /**
     * 设置使用模块的平台,建立模块与平台的关系
     *
     * @param platform
     */
    public void setPlatform(ModulePlatform platform) {
        off();
        mPlatformOwner = new WeakReference<>(platform);
        platform.addModule(this);
    }

    /**
     * 从平台中移除模块,移除之后将切断与平台之间的联系
     */
    public void off() {
        if (checkPlatformOwnerIsNull()) return;
        mPlatformOwner.get().removeModule(this);
        mPlatformOwner = null;
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
    public void startActivity(Intent intent) {
        if (checkPlatformOwnerIsNull()) return;
        mPlatformOwner.get().startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        if (checkPlatformOwnerIsNull()) return;
        mPlatformOwner.get().startActivity(intent, options);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (checkPlatformOwnerIsNull()) return;
        mPlatformOwner.get().startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (checkPlatformOwnerIsNull()) return;
        mPlatformOwner.get().startActivityForResult(intent, requestCode, options);

    }

    @Override
    public Context getContext() {
        if (checkPlatformOwnerIsNull()) return null;
        return mPlatformOwner.get().getContext();
    }

    @Override
    public Activity getActivity() {
        if (checkPlatformOwnerIsNull()) return null;
        return mPlatformOwner.get().getActivity();
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        if (checkPlatformOwnerIsNull()) return null;
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
        return mPlatformOwner == null || mPlatformOwner.get() == null;
    }
}
