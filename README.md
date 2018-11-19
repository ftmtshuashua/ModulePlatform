# [ModulePlatform](https://github.com/ftmtshuashua/ModulePlatform) 简介


`ModulePlatform`虚拟的模块化平台,我们可以把它当作一个虚拟的Activity或者Fragment.
在它上面运行的模块可以获得和Activity或者Fragment完全一致的体验.
我们将业务流程模块化之后再将他们自由组合来实现更为复杂的业务,并且不会在Activity或者Fragment中看到臃肿的代码.
每个单独的模块也有一个很清晰的流程非常容易维护和拓展.

>通过业务的模块化,我们不再关心业务的流程,只需要知道调用模块并得到返回值就可以了

## 配置依赖

设置依赖项
```
implementation 'support.lfp:ModulePlatform:1.0.0'
```
该项目在AndroidX基础上搭建,需要一下库:
```
implementation 'androidx.fragment:fragment:1.0.0+'
implementation 'androidx.appcompat:appcompat:1.0.0+'
```

## 搭建模块化平台
```
1.创建所有Activity和Fragment的基类,BaseActivity和BaseFragment
2.将BaseActivity和BaseFragment分别继承于MPActivity和MPFragment(如果你已经继承了其他第三方的基类，可以直接拷贝MP**里面的源码到你的BaseActivity和BaseFragment中)
3.完成.现在可以在任何子类中调用getPlatform(）获得一个虚拟的模块化平台
```
第二步：创建业务模块
```
//例子 - 创建一个模块并且继承Module,(Module中模拟的Activity和Fragment的环境,启动包含他们的生命周期方法和一些API)
public class ModuleTakePicture extends Module {
      public ModuleTakePicture(ModulePlatform platform) {
           super(platform);
       }

       //实现模块业务
       static final int REQUEST_CODE_TAKE_PICTURE = 15452;
       Action1<Bitmap> mAction;
       /** 调用系统相机拍照并返回数据 */
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


//上面代码实现了调用相机并且获得Bitmap数据然后通过Action1接口回调的业务流程
//下面再看看看Activity中的实现
void function(){
        ModuleTakePicture(getPlatform()).takePicture(Action1 {
            view_ImageView.setImageBitmap(it)
        })
}
//只需要一句话就实现了照相的流程，并且使得照相模块独立出去
//提示：我们还可以在模块中使用其他模块，比如权限管理。

```

## 问题反馈

如果你在使用ModulePlatform中遇到任何问题可以提[Issues](https://github.com/ftmtshuashua/ModulePlatform/issues)出来。另外欢迎大家为ModulePlatform贡献智慧，欢迎大家[Fork and Pull requests](https://github.com/ftmtshuashua/ModulePlatform)。

如果觉得对你有用的话，点一下右上的星星赞一下吧。

## LICENSE

```
Copyright (c) 2018-present, ModulePlatform Contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
