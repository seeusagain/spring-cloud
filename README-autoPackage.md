# windows微服务自动打包、发布
## 功能
- maven打包
- 把maven 生成的jar包移动到指定文件夹
- 自动运行jar


## 使用
- 在当前木新建bat文件，吧下面的命令复制进去运行即可

## 自动打包、拷贝jar包
``` java
start mvn clean package

pause
echo continue pause

rem -------------------------------------------------------------------
rem -------------------------------------------------------------------
rem       copy jar to dir spring-cloud-autopackage
rem -------------------------------------------------------------------
rem -------------------------------------------------------------------
set autopackageDir=spring-cloud-autopackage
rmdir /s/q %autopackageDir%
md %autopackageDir%

copy microservice-eureka\target\microservice-eureka.jar %autopackageDir%
copy microservice-basic\target\microservice-basic.jar %autopackageDir%
copy microservice-example\target\microservice-example.jar %autopackageDir%
copy microservice-zuul\target\microservice-zuul.jar %autopackageDir%

rem -------------------------------------------------------------------
rem -------------------------------------------------------------------
rem       					ALL DONE!
rem -------------------------------------------------------------------
rem -------------------------------------------------------------------

@echo off
pause
echo continue pause
```

## 自动发布，先发布注册中心
``` java
set baseDir=%cd%
set autopackageDir=spring-cloud-autopackage

start cmd /k  "java -jar %baseDir%\%autopackageDir%\microservice-eureka.jar"

rem -------------------------------------------------------------------
rem -------------------------------------------------------------------
rem The command will continue to execute after 15 seconds of sleep ....
rem -------------------------------------------------------------------
rem -------------------------------------------------------------------
@echo off
ping -n 30 127.1>nul

start cmd /k  "java -jar %baseDir%\%autopackageDir%\microservice-basic.jar"
start cmd /k  "java -jar %baseDir%\%autopackageDir%\microservice-example.jar"
start cmd /k  "java -jar %baseDir%\%autopackageDir%\microservice-zuul.jar"
```


