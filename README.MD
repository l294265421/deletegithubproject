# 批量删除github上的项目

## 项目源起
之前在github上了放了很多项目，但是大部分都是因为学习一项工具或者新技术时用示例代码创建的，意义不大，于是决定先把这部分项目导入到私有的gitlab里，然后在github里删除这部分项目；问题来了，github里删除一个项目比较繁琐，删除大量项目更是耗时耗力，于是写了这个项目用于批量删除github里的项目

## 使用方法
1. 先配置conf目录下的project_name和user_info.properites文件，project_name文件里放入要删除的项目名称，一行一个；user_info.properites文件配置github登陆名、密码和用户名。
2. 运行com.liyuncong.application.deletegithubproject.main.Main文件

## 注意
1. 测试SeleniumDeleter使用的chrome浏览器版本为58.0.3029.110 (64-bit)