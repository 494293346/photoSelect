# photoSelect
从本地相册中选择一张图片，跳转到截图界面，截图后获取并显示截取后的图片，可用于头像上传等操作中。
##简介
 ```
该模块功能由项目中提取出来，存在诸多地方需要完善，如有需要，请下载并按照自己的需求自行修改。
1.imageload
该项目的图片加载依赖于imageload，如果是用Fresco或者其他图片加载框架的同学，请自行将代码中的imageload部分去掉，
并添加自己的三方库。

2.PhotoUtils
该功能中最核心类，用于开启异步任务，获取相册的相关信息及相册中的相片信息。

3.屏幕大小的获取及使用
由于打开相册后的相片缩略图是根据屏幕的比例进行缩放的，而在原项目中屏幕的大小获取方法是在程序启动时，获取并存
储于SharedPreferences，需要用到时再从SharedPreferences中获取。而在该项目中获取方法只是MainActivity—— initSet()，并且对
SharedPreferences的使用没有做任何封装处理，而使用到屏幕比例的方法也是放在PhotoGridAdapter中的getDispalyWidth，请使用
时记得对该方法进行完善。

```
##效果图展示
![图片描述](https://github.com/494293346/photoSelect/blob/master/images/show.gif)
