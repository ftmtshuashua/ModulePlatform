package support.lfp.ma.base;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import androidx.annotation.Nullable;

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
                    SoftInputIsVisible = Utils.isSoftInputVisible(this);
                    if (SoftInputIsVisible) {
                        TouchView = Utils.findViewByXY(this, ev.getX(), ev.getY());
                        IsHiddenAtUp = TouchView == null || !(TouchView instanceof EditText);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.pow(Math.pow(TouchX - ev.getX(), 2) + Math.pow(TouchY - ev.getY(), 2), 0.5) >= TouchSlop) {
                        IsHiddenAtUp = false;
                        Utils.hideSoftInput(this);
                        getCurrentFocus().clearFocus();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (IsHiddenAtUp) {
                        Utils.hideSoftInput(this);
                        getCurrentFocus().clearFocus();
                    }
                    TouchView = null;
                    break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }


}


