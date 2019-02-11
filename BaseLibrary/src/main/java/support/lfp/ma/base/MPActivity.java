package support.lfp.ma.base;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
public class MPActivity extends LifecycleActivity {
    private boolean isSmartSoftKeyboard = true;//一个聪明的软键盘
    boolean IsHiddenAtUp = false; //是否需要在手指抬起的时候隐藏软键盘
    View TouchView;
    boolean SoftInputIsVisible = false; //软键盘已经显示
    int TouchSlop;
    float TouchX, TouchY;
    List<View> FilterArray;/*过滤的View，被包含在该集合下的View不会导致软键盘关闭*/

    /**
     * 设置是否启用一个聪明的软键盘。启用的时候系统会自动判断触摸位置是否为输入框，如果不是一个输入框
     * 系统会自动的隐藏软键盘，并且清理焦点.
     *
     * @param isSmart 是否启用，默认是启用的
     */
    protected void setSmartSoftKeyboard(boolean isSmart) {
        this.isSmartSoftKeyboard = isSmart;
    }

    /**
     * 获得过滤View，被过滤的View不会导致软键盘关闭
     *
     * @param v 需要过滤的View
     */
    protected void addSmartSoftKeyboaryFilterView(View v) {
        if (FilterArray == null) {
            FilterArray = new ArrayList<>();
        }
        FilterArray.add(v);
    }

    /**
     * 移除过滤View，移除之后当用户触摸到该View的时候会导致软键盘被隐藏
     *
     * @param v 需要从过滤中移除的View
     */
    protected void deleteSmartSoftKeyboaryFilterView(View v) {
        if (FilterArray != null) {
            FilterArray.remove(v);
        }
    }

    //判断过滤器里面是否包含View
    private boolean isContainsFilterView(View v) {
        if (v == null) return false;
        return FilterArray == null ? false : FilterArray.contains(v);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isSmartSoftKeyboard) return super.dispatchTouchEvent(ev);
        if (SoftInputIsVisible || ev.getAction() == MotionEvent.ACTION_DOWN) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    TouchX = ev.getX();
                    TouchY = ev.getY();
                    View focuseView = getCurrentFocus();
                    SoftInputIsVisible = Utils.isSoftInputVisible(this) || (focuseView != null && focuseView instanceof EditText);
                    if (SoftInputIsVisible) {
                        TouchView = Utils.findViewByXY(this, ev.getX(), ev.getY());
                        IsHiddenAtUp = !isContainsFilterView(TouchView) && (TouchView == null || !(TouchView instanceof EditText));
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.pow(Math.pow(TouchX - ev.getX(), 2) + Math.pow(TouchY - ev.getY(), 2), 0.5) >= TouchSlop) {
                        IsHiddenAtUp = false;
                        if (Utils.isSoftInputVisible(this)) Utils.hideSoftInput(this);
                        final View currentFocus = getCurrentFocus();
                        if (currentFocus != null) currentFocus.clearFocus();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (IsHiddenAtUp) {
                        if (Utils.isSoftInputVisible(this)) Utils.hideSoftInput(this);
                        final View currentFocus = getCurrentFocus();
                        if (currentFocus != null) currentFocus.clearFocus();
                    }
                    TouchView = null;
                    break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }


}


