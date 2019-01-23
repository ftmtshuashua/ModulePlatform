package support.lfp.ma.base;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

/**
 * <pre>
 * Tip:
 *
 * Function:
 *
 * Created by LiFuPing on 2018/12/5 14:33
 * </pre>
 */
class Utils {
    public static boolean isSoftInputVisible(final Activity activity) {
        return isSoftInputVisible(activity, 200);
    }

    public static boolean isSoftInputVisible(final Activity activity, final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        if(contentViewChild==null)return 0;
        final Rect outRect = new Rect();
        contentViewChild.getWindowVisibleDisplayFrame(outRect);
        return contentViewChild.getBottom() - outRect.bottom;
    }

    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 根据屏幕的坐标值，获得坐标对应的View
     *
     * @param activity The activity
     * @param x        相对于屏幕的X坐标
     * @param y        相对于屏幕的Y坐标
     * @return 坐标对应的View
     */
    public static final View findViewByXY(Activity activity, float x, float y) {
        View root = activity.findViewById(android.R.id.content);
        return findViewByXY(root, x, y);
    }

    /*遍历View查找点击位置对应的View*/
    private static final View findViewByXY(View parent, float x, float y) {
        if (parent instanceof ViewGroup) {
            View touchview = parent;
            ViewGroup a_parent = (ViewGroup) parent;
            int count = a_parent.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                View child = a_parent.getChildAt(i);
                if (child.getVisibility() != View.VISIBLE) continue;
                int[] position = new int[2];
                child.getLocationOnScreen(position);
                int left = position[0];
                int top = position[1];
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                if (y >= top && y <= bottom && x >= left && x <= right) {
                    touchview = findViewByXY(child, x, y);
                    break;
                }

            }
            return touchview;
        } else return parent;
    }
}
