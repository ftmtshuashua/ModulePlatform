package support.lfp.ma.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import support.lfp.ma.interior.ImlModulePlatformApi;
import support.lfp.ma.interior.ImlModulePlatformContext;
import support.lfp.ma.interior.ImlModulePlatformLifecycle;
import support.lfp.ma.interior.ImlModulePlatformOwner;

import java.util.Enumeration;
import java.util.Vector;

/**
 * <pre>
 * Tip:
 *      虚拟模块平台(模块的基石) - 所有模块都在个平台上运作
 *
 * Function:
 *      getPlatformOwner()  :获得模块化平台实例
 *      getContext()        :获得平台关联的Context对象
 *      getActivity()       :获得平台关联的Activity对象
 *
 *
 * Created by LiFuPing on 2018/11/12 17:19
 * </pre>
 */
public class ModulePlatform implements ImlModulePlatformLifecycle, ImlModulePlatformContext, ImlModulePlatformApi {
    private final ImlModulePlatformOwner mModulePlatformOwner;
    private final Vector<Module> module_arrays;

    public ModulePlatform(ImlModulePlatformOwner provider) {
        mModulePlatformOwner = provider;
        module_arrays = new Vector<>();
    }

    /**
     * 平台底层对象，如Activity获得Fragment
     *
     * @return 平台底层对象
     */
    public ImlModulePlatformOwner getPlatformOwner() {
        return mModulePlatformOwner;
    }

    /**
     * 添加一个模块到平台上，当平台发生变化的时候反映到模块中
     *
     * @param module The module
     */
    @MainThread
    public void addModule(@NonNull Module module) {
        module_arrays.add(module);
    }

    /**
     * 从平台中移除模块
     *
     * @param module The module
     */
    @MainThread
    public void removeModule(@NonNull Module module) {
        module_arrays.remove(module);
    }


    @Override
    public void onCreate() {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onCreate();
            }
        });
    }

    @Override
    public void onStart() {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onStart();
            }
        });
    }

    @Override
    public void onResume() {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onResume();
            }
        });
    }

    @Override
    public void onPause() {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onPause();
            }
        });
    }

    @Override
    public void onStop() {

        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onStop();
            }
        });
    }

    @Override
    public void onDestroy() {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onDestroy();
                module.off();
            }
        });
    }

    @Override
    public Context getContext() {
        if (checkPlatformOwnerIsNull()) return null;
        return mModulePlatformOwner.getContext();
    }

    @Override
    public Activity getActivity() {
        if (checkPlatformOwnerIsNull()) return null;
        return mModulePlatformOwner.getActivity();
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        if (checkPlatformOwnerIsNull()) return null;
        return mModulePlatformOwner.getSmartFragmentManager();
    }

    @Override
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onRestoreInstanceState(savedInstanceState);
            }
        });
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onSaveInstanceState(outState);
            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onActivityResult(requestCode, resultCode, data);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        ForEach.map(module_arrays, new ForEach.Action() {
            @Override
            public void call(Module module) {
                module.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        });
    }

    @Override
    public void startActivity(Intent intent) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.startActivityForResult(intent, requestCode, options);

    }


    boolean checkPlatformOwnerIsNull() {
        return mModulePlatformOwner == null;
    }


    /**
     * 遍历集合
     */
    private static class ForEach {

        static void map(Vector<Module> vector, Action action) {
            Enumeration<Module> enume = vector.elements();
            while (enume.hasMoreElements()) {
                Module module = enume.nextElement();
                action.call(module);
            }
        }


        interface Action {
            void call(Module module);
        }

    }

}
