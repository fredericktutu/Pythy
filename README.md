# Pythy
Python-Hongyi Programming Language

## 文件架构
+ Pythy
	+ src(java源文件)
		+ parser
		+ execute
		+ gui
		+ test
	+ class(class文件)
		+ pythy
			+ parser
			+ execute
			+ gui
			+ test
	+ files(运行时需要用到的测试样例等)

## 编译与运行指南
本项目中，java源文件和class文件分开放置，分别放在src目录和class目录下，在这样的架构下，需要在编码、代码编译、代码运行三个地方做出一些调整


比如我要编写一个测试`test1.java`来测试编译和执行,这是需要在文件的开头做出的声明:
```java
package pythy.test 		  //说明test1.java是在pythy.test这个包下面
import pythy.parser.*;    //测试代码需要引用parser的部分
import pythy.execute.*;   // 同样，需要引用执行的部分
```

那么在编译的时候，我们应该在根目录下输入下面的命令.
```shell
Pythy> javac src/test/test1.java -classpath class -d class 

```
其中，第一个参数是我要编译的`test1.java`的相对路径；第二个`-classpath`参数是`class`文件夹的相对路径，代表这个包的根目录；第三个`-d`参数也是`class`文件夹，即代表把`test1.class`输出到class文件夹下面，又因为我们是按照包来组织的，所以会自动把`test1.class`放在`class`文件夹下面的`pythy/test`中。

在运行的时候,类似,假如我们处于根目录,也需要交代`classpath`参数
```
Pythy> java class/pythy/test1 -classpath class
```