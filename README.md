# FunnyQQ(60%)
腾讯QQ - FQDK,该应用理论上可实现QQ的基本功能,还可以利用此应用做一些有意思的东西,比如QQ机器人,QQ轰炸机(被封QQ不要来找我),QQ服务...
#### 说明<br>

* 登录:现在只开启了扫码登录,请求获取的图片放在根目录下
* 配置:该应用的所有配置(eg. 参数,请求的url,均为static)都放在 Config 类中,如要改变请自己测试类的main或构造函数里调用 `Config.参数名=` 改变.
* 入口:测试类为 QRCodeLogin,使用时可用该类或仿造该类创建自己的入口类.
* 关于缓存:该应用数据暂时都保存在内存,默认开启,如要关闭(不推荐)请设置`Config.cache=fals`
* 持久化:正在想

#### 版本号<br>
v1.0.2<br>

#### 更新日志<br>
    [v1.0.2] 17-03-04 12:00 : 添加了缓存,架构更改,进度60%
    
    [v1.0.1] 17-03-03 22:00 : 架构基本完成,实现了登录,获取个人信息
    
    [v1.0.0] 17-02-26 13:30 : 开始架构,分析
    
#### 开发者<br>
    现在只有苦逼的我,欢迎加入,QQ:545640807,e-mail:54564087@qq.com
