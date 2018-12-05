package support.lfp.ma.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * <pre>
 * Tip:
 *      搭载了模块化平台的Activity
 *
 * Function:
 *
 *
 *
 * Created by LiFuPing on 2018/11/12 17:16
 * </pre>
 */
public class MPActivity extends AppCompatActivity implements ModulePlatformOwner {
    private ModulePlatform mPlatfrom;
    private PlatformLifecycleObserver mPlatformLifecycleObserver;
    private boolean isSmartSoftKeyboard = true;//一个聪明的软键盘

    /**
     * 设置是否启用一个聪明的软键盘。启用的时候系统会自动判断触摸位置是否为输入框，如果不是一个输入框
     * 系统会自动的隐藏软键盘，并且清理焦点.
     *
     * @param isSmart 是否启用，默认是启用的
     */
    protected void setSmartSoftKeyboard(boolean isSmart) {
        this.isSmartSoftKeyboard = isSmart;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlatfrom = new ModulePlatform(this);
        mPlatformLifecycleObserver = new PlatformLifecycleObserver(mPlatfrom);
        getLifecycle().addObserver(mPlatformLifecycleObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mPlatformLifecycleObserver);
    }

    @Override
    public ModulePlatform getPlatform() {
        return mPlatfrom;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public FragmentManager getSmartFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPlatfrom.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isSmartSoftKeyboard && ev.getAction() == MotionEvent.ACTION_DOWN && Utils.isSoftInputVisible(this)) {
            View touchview = Utils.findViewByXY(this, ev.getX(), ev.getY());
            View focusview = getCurrentFocus();

            if (touchview == null || !(touchview instanceof EditText)) {
                Utils.hideSoftInput(this);
                focusview.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}


