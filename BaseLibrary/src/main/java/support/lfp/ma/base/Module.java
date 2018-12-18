package support.lfp.ma.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import support.lfp.ma.interior.ImlModulePlatformApi;
import support.lfp.ma.interior.ImlModulePlatformContext;
import support.lfp.ma.interior.ImlModulePlatformLifecycle;

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
public class Module implements ImlModulePlatformContext, ImlModulePlatformLifecycle, ImlModulePlatformApi {
    private ModulePlatform mPlatformOwner;

    public Module(ModulePlatform platform) {
        setPlatform(platform);
    }

    /**
     * 设置使用模块的平台,建立模块与平台的关系
     *
     * @param platform 平台
     */
    public void setPlatform(ModulePlatform platform) {
        off();
        if (platform != null) {
            mPlatformOwner = platform;
            mPlatformOwner.addModule(this);
        }
    }

    /**
     * 获得模块化平台
     *
     * @return 模块化平台
     */
    public ModulePlatform getPlatform() {
        return mPlatformOwner;
    }

    /**
     * 从平台中移除模块,移除之后将切断与平台之间的联系
     */
    public void off() {
        if (mPlatformOwner == null) return;
        mPlatformOwner.removeModule(this);
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
        checkPlatformOwnerIsNull();
        mPlatformOwner.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        checkPlatformOwnerIsNull();
        mPlatformOwner.startActivity(intent, options);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        checkPlatformOwnerIsNull();
        mPlatformOwner.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        checkPlatformOwnerIsNull();
        mPlatformOwner.startActivityForResult(intent, requestCode, options);

    }

    @Override
    public Context getContext() {
        checkPlatformOwnerIsNull();
        return mPlatformOwner.getContext();
    }

    @Override
    public Activity getActivity() {
        checkPlatformOwnerIsNull();
        return mPlatformOwner.getActivity();
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        checkPlatformOwnerIsNull();
        return mPlatformOwner.getSmartFragmentManager();
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


    private void checkPlatformOwnerIsNull() {
        if (mPlatformOwner == null) throw new NullPointerException("请将模块关联到平台上");
    }
}
