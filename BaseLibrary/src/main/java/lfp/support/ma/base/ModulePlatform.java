package lfp.support.ma.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.Enumeration;
import java.util.Vector;

/**
 * <pre>
 * Tip:
 *      模块平台(模块的基石) - 所有模块都在个平台上运作
 *
 * Function:
 *      getPlatform()       :获得模块化平台实例
 *      getContext()        :获得平台关联的Context对象
 *      getActivity()       :获得平台关联的Activity对象
 *
 *
 * Created by LiFuPing on 2018/11/12 17:19
 * </pre>
 */
public class ModulePlatform implements ModulePlatformLifecycle, ModulePlatformContext, ModulePlatformApi {
    private final WeakReference<ModulePlatformOwner> mModulePlatformOwner;
    private final Vector<Module> module_arrays;

    public ModulePlatform(ModulePlatformOwner provider) {
        mModulePlatformOwner = new WeakReference<>(provider);
        module_arrays = new Vector<>();
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
        return mModulePlatformOwner.get().getContext();
    }

    @Override
    public Activity getActivity() {
        if (checkPlatformOwnerIsNull()) return null;
        return mModulePlatformOwner.get().getActivity();
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        if (checkPlatformOwnerIsNull()) return null;
        return mModulePlatformOwner.get().getSmartFragmentManager();
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
        mModulePlatformOwner.get().startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.get().startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.get().startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (checkPlatformOwnerIsNull()) return;
        mModulePlatformOwner.get().startActivityForResult(intent, requestCode, options);

    }


    boolean checkPlatformOwnerIsNull() {
        return mModulePlatformOwner.get() == null;
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
