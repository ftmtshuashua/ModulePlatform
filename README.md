# [ModulePlatform](https://github.com/ftmtshuashua/ModulePlatform) 简介


`ModulePlatform` 是一个虚拟的模块化平台，所有模块都运行在它上面。在它上面运行的模块可以获得和在Activity或者Fragment中一致的体验。使用它可以很轻松的把复杂的业务逻辑拆分成独立的可复用的独立模块。

>通过业务的模块化,我们不再关心业务的过程


## 配置依赖

在项目的build.gradle中添加
```
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```
在Model的build.gradle中添加 [![](https://jitpack.io/v/ftmtshuashua/ModulePlatform.svg)](https://jitpack.io/#ftmtshuashua/ModulePlatform)
```
dependencies {
    implementation 'com.github.ftmtshuashua:ModulePlatform:version'
}
```
该项目在AndroidX基础上搭建,需要以下库
```
implementation 'androidx.fragment:fragment:version'
implementation 'androidx.appcompat:appcompat:version'
```

#### 简单使用

第一步:让Activity和Fragment继承MPActivity与MPFragment
```
public class BaseActivity extends MPActivity{}
public class BaseFragment extends MPFragment{}
```
第二步:创建业务模块

```
//例子 - 拍照之后获得图片
public class ModuleTakePicture extends Module {
      public ModuleTakePicture(ModulePlatform platform) {super(platform);}

       Action1<Bitmap> mAction; //回调，获得照骗

       /** 调用系统相机拍照并返回数据 */
       public void takePicture(Action1<Bitmap> action1) {
           mAction = action1;
           startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 666666);
       }

       @Override //获得照片数据
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           if (666666 == requestCode) {
               if (resultCode == Activity.RESULT_OK) {
                   Toast.makeText(getContext(), "拍照成功", Toast.LENGTH_LONG).show();
                   if (mAction != null) mAction.call((Bitmap) data.getExtras().get("data"));
               } else Toast.makeText(getContext(), "调用相机拍照失败", Toast.LENGTH_LONG).show();
           }
       }
}
```
第三步:使用模块
```
public class BaseActivity extends MPActivity {
    ImageView mIV_ImageView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIV_ImageView = findViewById(R.id.view_ImageView);
        findViewById(R.id.view_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ModuleTakePicture(getPlatform()).takePicture(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        mIV_ImageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
```
>将多个模块自由组合起来实现更为复杂的业务处理


#### 问题反馈

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
