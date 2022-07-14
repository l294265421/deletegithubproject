# 批量删除 github 上的项目

## 项目源起
之前在github上了放了很多项目，但是大部分都是因为学习一项工具或者新技术时用示例代码创建的，意义不大，于是决定先把这部分项目导入到私有的gitlab里，然后在github里删除这部分项目；问题来了，github里删除一个项目比较繁琐，删除大量项目更是耗时耗力，于是写了这个项目用于批量删除github里的项目

## 使用方法
1. 先配置 conf 目录下的 `project_name` 和 `user_info.properties` 文件，`project_name` 文件里放入要删除的项目名称，一行一个；`user_info.properties` 文件配置 github 登陆名、密码和用户名。
2. 配置 conf 目录下的 `browser.properties` 文件
   > browser: 浏览器可执行程序路径<br>
   driver: 对应平台的浏览器驱动程序路径
3. 运行 `com.liyuncong.application.deletegithubproject.main.Main` 文件

## 注意
1. 浏览器及浏览器驱动版本注意配对
2. [Chrome 浏览器驱动下载][1]


[1]: http://chromedriver.chromium.org/downloads