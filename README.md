这是一款基于ijkplayer的MaterialDesign视频播放器
 
   >开发环境AndroidStudio2.1.1
   gradleVersion = '2.10'
   compileSdkVersion 23
   buildToolsVersion 23.0.3
   ndk版本android-ndk-r10e 
   
 -- --

效果图:

![主页](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-21-31.png)

![](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-35-01.png)

![](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-22-45.png)

![](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-25-10.png)

![](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-31-16.png)

![](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-32-58.png)

![](https://raw.githubusercontent.com/shonegg/PaPaPlayer/master/ScreenShots/Screenshot_2016-07-23-12-35-37.png)

1||支持网络媒体和本地视频播放
2||这里,我已经编译好了ijkplayer的解码播放库,so文件已经放到jniLibs下面了,不过最好是自己亲自动手编译一下jni文件,对播放多媒体感兴趣的小伙伴,可以带着问题深入研究下这个基于FFmpeg的开源框架(SurfaceView用法\MediaPlayer播放\解码渲染\播放\暂停\前进后退\记录上次进度\缓存\插播广告\屏幕旋转,全屏\弹幕)
3||出于对视频网站版权的考虑,我把Api的url地址屏蔽掉了,请见谅,有兴趣的,自己可以试着去网上抓数据

依赖库:
网络方面:okhttp
图片加载:universalimageloader(封装一层,降低依赖)
数据库:greendao
随机颜色:com.github.lzyzsd.randomcolor
多主题:magicasakura(暂未处理)

UI效果:
1| 首页向上滚动,Appbar折叠,Toolbar变色
2| 状态栏沉浸
3| 瀑布流展示专题
4| 视频详情头部背景,毛玻璃效果,向上滚动,Toolbar变色
5| 卡片布局,水波纹
6| NestedScrollView嵌套RecyclerView
7| SwipeRefreshLayout刷新4种颜色交替

项目的主色调为饱和度500的蓝色Blue :#5677fc
ps材料设计规范:
工具栏和大色块适合使用饱和度500的基础色，这也是你应用的主要颜色。状态栏适合使用更深一些的饱和度700的基础色。
图标自己用Photoshop cc 2015处理,需要破解,破解工具就是在不联网的情况下用注册机,修改下hosts文件~

项目开源仅供参考,可以发挥自己的创意,做自己喜欢的app