package lfp.support.app.module;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;
import lfp.support.app.base.Action1;
import lfp.support.ma.base.Module;
import lfp.support.ma.base.ModulePlatform;

/**
 * <pre>
 * Tip:
 *      拍照模块
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/13 17:40
 * </pre>
 */
public class ModuleTakePicture extends Module {
    public static final int REQUEST_CODE_TAKE_PICTURE = 15452;

    Action1<Bitmap> mAction;

    public ModuleTakePicture(ModulePlatform platform) {
        super(platform);
    }

    /**
     * 调用系统相机拍照并返回数据
     */
    public void takePicture(Action1<Bitmap> action1) {
        mAction = action1;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_TAKE_PICTURE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getContext(), "拍照成功", Toast.LENGTH_LONG).show();
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                if (mAction != null) mAction.call(bm);
            } else Toast.makeText(getContext(), "调用相机拍照失败", Toast.LENGTH_LONG).show();
        }
    }
}
