package lfp.support.ma.base;

/**
 * <pre>
 * Tip:
 *      模块平台生命周期
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/13 11:28
 * </pre>
 */
interface ModulePlatformLifecycle {
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
