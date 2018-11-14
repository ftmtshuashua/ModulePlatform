package lfp.support.ma.base;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.FragmentManager;

/**
 * <pre>
 * Tip:
 *      模块平台上下文
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/13 11:29
 * </pre>
 */
interface ModulePlatformContext {

    /**
     * 获得平台关联的Context对象
     *
     * @return The context
     */
    Context getContext();

    /**
     * 获得平台关联的Activity对象
     *
     * @return The activity
     */
    Activity getActivity();

    /**
     * 获得FragmentManager
     *
     * @return 获得平台的FragmentManager
     */
    FragmentManager getSmartFragmentManager();

}
