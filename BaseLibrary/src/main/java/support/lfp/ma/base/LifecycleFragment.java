package support.lfp.ma.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * <pre>
 * Tip:
 *      虚拟平台相关维护
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/5 15:43
 * </pre>
 */
@SuppressLint("ValidFragment")
class LifecycleFragment extends Fragment implements ModulePlatformOwner {

    private ModulePlatform mPlatfrom;
    private PlatformLifecycleObserver mPlatformLifecycleObserver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlatfrom = new ModulePlatform(this);
        mPlatformLifecycleObserver = new PlatformLifecycleObserver(mPlatfrom);
        getLifecycle().addObserver(mPlatformLifecycleObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mPlatformLifecycleObserver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public ModulePlatform getPlatform() {
        return mPlatfrom;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        return getChildFragmentManager();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mPlatfrom.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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
