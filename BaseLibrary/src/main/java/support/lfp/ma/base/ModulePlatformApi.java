package support.lfp.ma.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 * Tip:
 *      平拍相关API
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/13 15:17
 * </pre>
 */
 interface ModulePlatformApi {

    void onRestoreInstanceState(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle outState);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void startActivity(Intent intent);
    void startActivity(Intent intent, @Nullable Bundle options);

    void startActivityForResult(Intent intent, int requestCode);
    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);
}
