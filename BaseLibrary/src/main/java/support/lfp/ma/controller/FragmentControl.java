package support.lfp.ma.controller;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import support.lfp.ma.base.ModulePlatform;

import java.util.List;

/**
 * <pre>
 * Tip:
 *      Fragment切换控制器,常用于类似首页都页面切换场景，帮助实现性能优化和防止内存泄漏
 *
 *      延时加载：即用即加载的原则，在显示的时候才会初始化对应Fragment,已提高性能
 *      缓存：加载过的Fragment不会被重复加载,防止内存泄漏
 *
 * Function:
 *      change()                        :切换Fragment
 *      setOnFragmentInitListener()     :设置Fragment初始化监听器
 *      setOnFragmentChangeListener()   :设置Fragment切换监听器
 *      getTag()                        :获得Fragment唯一标识
 *      isTag()                         :判断当前Framgnet是否为标记对应的Fragment
 *      getCurrentFragment()            :获得当前Framgnet
 *      getFragmentByTag()              :通过标记获得Fragment
 *      onInit()                        :当Fragment初始化回调
 *      onChange()                      :当Fragment切换回调
 *
 * Created by LiFuPing on 2018/6/1
 * </pre>
 */
public abstract class FragmentControl<T> {

    private FragmentManager mFragmentManager;
    /*Fragment放置的目标容器ID*/
    private int mCcontainerViewId;
    /*当前显示中的Fragment*/
    private Fragment mCurrentFragment;
    /*Fragment初始化监听器*/
    private OnFragmentInitListener mOnFragmentInit;
    /*Fragment切换监听器*/
    private OnFragmentChangeListener mOnFragmentChange;


    /**
     * 使用虚拟平台创建控制器，在Activity和Fragment中获得的FragmentManager是不同的，虚拟平台能正确的识别他们
     *
     * @param platform        虚拟平台
     * @param containerViewId Framgent的容器id
     */
    public FragmentControl(@NonNull ModulePlatform platform, @LayoutRes int containerViewId) {
        mCcontainerViewId = containerViewId;
        mFragmentManager = platform.getSmartFragmentManager();
        recover();
    }

    /**
     * 通过Activity创建控制器
     *
     * @param activity        the activity
     * @param containerViewId Framgent的容器id
     */
    public FragmentControl(@NonNull FragmentActivity activity, @LayoutRes int containerViewId) {
        mCcontainerViewId = containerViewId;
        mFragmentManager = activity.getSupportFragmentManager();
        recover();
    }

    /**
     * 通过Fragment创建控制器
     *
     * @param fragment        the fragment
     * @param containerViewId Framgent的容器id
     */
    public FragmentControl(@NonNull Fragment fragment, @LayoutRes int containerViewId) {
        mCcontainerViewId = containerViewId;
        mFragmentManager = fragment.getChildFragmentManager();
        recover();
    }

    /*一些情况下导致缓存信息被回收。在这里进行恢复 */
    private void recover() {
        List<Fragment> array = mFragmentManager.getFragments();
        if (array == null || array.isEmpty()) return;
        for (Fragment f : array) {
            if (f.getId() != mCcontainerViewId) continue;
            if (f.isAdded() && !f.isHidden()) mCurrentFragment = f;
        }
    }



    /**
     * 设置Fragment初始化监听器
     *
     * @param l 初始化监听器
     */
    public void setOnFragmentInitListener(OnFragmentInitListener<? super T> l) {
        mOnFragmentInit = l;
    }

    /**
     * 设置Fragment切换监听器
     *
     * @param l 切换监听器
     */
    public void setOnFragmentChangeListener(OnFragmentChangeListener<? super T> l) {
        mOnFragmentChange = l;
    }


    /**
     * 通过数据源获得Fragment的Tag属性值,Tag属性用于标记Fragment
     */
    public String getTag(T tag) {
        return String.valueOf(tag);
    }

    /**
     * 判断当前显示的Fragment是否为tag标记的Fragment
     *
     * @param tag Fragment私有的唯一标记
     * @return 如果Fragment是tag对应的Fragment则return true
     */
    public boolean isTag(T tag) {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) return false;
        return getTag(tag).equals(fragment.getTag());
    }

    /**
     * 如果有Fragment是显示的,获得当前显示的Fragment,如果没有显示的Fragment则会获得null
     *
     * @return 当前显示的Fragment
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }


    /**
     * 切换到tag对应的Fragment，并且隐藏旧的Fragment。
     *
     * @param tag fragment的唯一标识
     */
    public void change(@NonNull T tag) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        onFragmentTransactionConfig(mFragmentTransaction);


        Fragment fragment = mFragmentManager.findFragmentByTag(getTag(tag));
        if (fragment != null && fragment == mCurrentFragment) return;

        List<Fragment> mData = mFragmentManager.getFragments();
        if (mData != null && !mData.isEmpty()) {
            for (Fragment f : mData) {
                if (f.getId() != mCcontainerViewId) continue;
                if (fragment != null && fragment == f) {
                    mCurrentFragment = f;
                    if (f.isAdded() && f.isHidden()) mFragmentTransaction.show(fragment);

                } else if (f.isAdded() && !f.isHidden()) {
                    if (f instanceof OnFragmentControlProcessor) {
                        ((OnFragmentControlProcessor) f).onFragmentHidden();
                    }
                    mFragmentTransaction.hide(f);
                }

            }
        }

        if (fragment == null) {
            fragment = getFragmentByTag(tag);
            if (fragment == null) return;
            mFragmentTransaction.add(mCcontainerViewId, fragment, getTag(tag));
            mFragmentTransaction.show(fragment);
            mCurrentFragment = fragment;
        }

        mFragmentTransaction.commit();

        onChange(fragment, tag);
        if (mOnFragmentChange != null) mOnFragmentChange.onFragmentChange(fragment, tag);


        if (fragment instanceof OnFragmentControlProcessor) {
            OnFragmentControlProcessor processor = (OnFragmentControlProcessor) fragment;
            if (fragment.isResumed()) processor.onFragmentShow();
        }

    }

    /**
     * 通过tag获得Fragment
     *
     * @param tag fragment的唯一标识
     * @return tag对于的fragment
     */
    public Fragment getFragmentByTag(T tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(getTag(tag));
        if (fragment == null) {
            fragment = onInit(tag);
            if (mOnFragmentInit != null) mOnFragmentInit.onFragmentInit(fragment, tag);
        }
        return fragment;
    }

    /**
     * 在切换的时候回调用FragmentTransaction，根据需求更改它的Style
     *
     * @param ft change -&gt;　FragmentTransaction
     */
    protected void onFragmentTransactionConfig(FragmentTransaction ft) {

    }

    /**
     * 通过tag创建，tag所对应的Fragment。这个tag是唯一指向这个Fragment的比标记
     *
     * @param tag T
     * @return Fragment
     */
    protected abstract Fragment onInit(T tag);

    /**
     * 当用户切换Framgent的时候回调
     *
     * @param tag      所显示Fragment对应的标记
     * @param fragment 当前显示的Fragment
     */
    protected void onChange(Fragment fragment, T tag) {

    }

    /**
     * 如果被FragmentControl控制的Fragment实现了OnFragmentControlProcessor，当用户切换到这个Fragment的时候会回调它的onFragmentShow和onFragmentHidden方法
     * 当调用 change(T tag) 的时候如果切换到Fragment已创建将会回调 onFragmentShow() 方法。
     * 使用onFragmentShow来表示从其他地方回到该Fragment的时机
     */
    public interface OnFragmentControlProcessor {
        /**
         * 当Fragment已经创建，从其他fragment回来的时候回调
         */
        void onFragmentShow();

        /**
         * 当Fragment已经创建，改变到其他fragment的时候回调
         */
        void onFragmentHidden();

    }

    /**
     * 监听Fragment切换
     */
    public interface OnFragmentChangeListener<T> {
        void onFragmentChange(Fragment fragment, T tag);
    }

    /**
     * 监听Fragment的初始化
     */
    public interface OnFragmentInitListener<T> {
        void onFragmentInit(Fragment fragment, T tag);
    }

}