package lfp.support.app

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import lfp.support.app.base.Action1
import lfp.support.app.module.ModuleTakePicture

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        ModuleTakePicture(platform).takePicture(Action1 {
            view_ImageView.setImageBitmap(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_Button.setOnClickListener(this)

    }
}
