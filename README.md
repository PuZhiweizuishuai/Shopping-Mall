# Shopping Mall

不挂高数商城

## In developing

正在开发中，未完成

## 注意事项

系统使用了 [renren-fast-vue](https://github.com/renrenio/renren-fast-vue) 作为前端管理页面，我在构建时碰到了几个坑，如果你也碰到了
那么具体解决方案如下：

1.使用`npm install`时，卡在 chromedriver，无法下载，导致`install`失败
  
解决方案：进入 \node_modules\chromedriver 目录下，修改 install.js

将第十六行的谷歌下载地址更换为 https://npm.taobao.org/mirrors/chromedriver 淘宝地址
然后再执行`npm install`

2.使用`npm run dev`出现 node-sass 异常

解决方案：参考 renren-fast-vue [issues56](https://github.com/renrenio/renren-fast-vue/issues/56)
最终方案如下：

先卸载：node-sass

```
npm uninstall --save node-sass
```

清除缓存

```
npm cache clean -f
```

升级node-sass模块

```
npm install --save node-sass
```

如果安装因为网络异常，请使用如下语句

```
npm install node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
```

最后执行

```
npm run dev
```

如有其它renren-fast-vue的问题，请参考renren-fast-vue文档