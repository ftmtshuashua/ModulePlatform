package lfp.support.ma.base;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * <pre>
 * Tip:
 *          Activity和Fragment生命周期观察
 * Function:
 *
 * Created by LiFuPing on 2018/11/13 16:13
 * </pre>
 */
public class PlatformLifecycleObserver implements GenericLifecycleObserver {

    ModulePlatform mModulePlatform;

    public PlatformLifecycleObserver(ModulePlatform platform) {
        mModulePlatform = platform;
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                mModulePlatform.onCreate();
                break;
            case ON_START:
                mModulePlatform.onStart();
                break;
            case ON_RESUME:
                mModulePlatform.onResume();
                break;
            case ON_PAUSE:
                mModulePlatform.onPause();
                break;
            case ON_STOP:
                mModulePlatform.onStop();
                break;
            case ON_DESTROY:
                mModulePlatform.onDestroy();
                break;
        }
    }
}
