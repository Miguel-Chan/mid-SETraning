# Vi, Java, Ant和Junit的自学报告

## Vi

Vi是在这次实训之前就接触过的工具，在之前主要是在ssh连接服务器的时候需要编辑服务器上的文件时会用到。Vi的使用相较于Sublime Text, VSCode这些在平时可能更常用的工具有着不小的区别，它分为不同的模式，如插入模式，命令模式等，而不是使用鼠标进行选择编辑等操作。在插入模式下输入文本与在普通的编辑器中的效果是一样的， 对于输入的内容会直接插入到编辑的文件，而在命令模式下的操作则是使用各种命令组合进行，比如x是删除单个字符，大写G是调至文档末尾，双击g是跳至文档开头，:wq是保存并退出等等。

相对于普通的文本编辑器，Vi毫无疑问是有着更陡峭的学习曲线的，但是熟练使用后也可以提高工作效率，而且通过各种命令可以使对文本的各种编辑操作都不需要通过移动鼠标或者按键盘方向键进行，对于一些人来说可能是一个很大的好处，甚至在使用IDE等工具中也可以安装Vim模式插件来在IDE中获得Vi的编辑体验。若使用Vim(Vi IMproved)的话，相对Vi还可以有更多的模式可使用，而且可以通过配置插件来获得很多的功能，比如补全、更换代码高亮主题等。



## Java

Java语言在实训前仅仅是稍微接触过，但是使用起来熟悉度不高。Java的语法和C++以及C#有不少的相似之处，运行需要先通过javac命令将java文件编译为class文件，再使用java命令用jvm虚拟机运行class文件。本次实训中并没有太多的时间去深入学习Java，因此只能讲之前使用其他OOP语言的经验套用到Java上。

在这周的内容中，Java编程主要用到的是Swing来实现GUI的计算器，主要用到了Swing中的`JTextField`, `JButton` 以及 `JFormattedTextField` 组件，其中`JTextField` 用于实现运算符号和计算结果的显示，`JButton` 用于实现计算器下半部分的按钮，而 `JFormattedTextField`  则用于实现数字的输入框，对于输入的数据会通过 `NumberFormatter` 来实现格式化，非数字或负号的输入将无效，且对每三位数字间将插入逗号。Swing 这方面的使用与之前使用Qt进行C++的GUI程序编写有着一定的相似之处，因此使用起来并不会感到完全的陌生。对于编程中碰到的一些问题，往往通过搜索引擎可以在 StackOverflow 中找到答案。



## Ant 和 Junit

Ant 和 Junit是这次实训前完全没有接触过的工具。其中 Ant 是一个命令行下的构建部署工具，在这次的使用中的用途与初级实训时使用MakeFile有点相似。而Junit则是一个Java单元测试框架

### Ant

对 Ant 的学习我主要参考的是 ant.apache.org 的 Tutorial。Ant 构建文件默认为项目根目录下的 `build.xml` . `build.xml` 中需先指定项目的名称`name` ，基目录`basedir` 以及默认运行的目标`default` 。接下来是编写`property` 元素，声明Ant项目中的各变量如 `src` 目录地址等。Ant 运行的单元是构建文件中的各目标`target` ，目标与目标之间可以存在依赖关系，如运行程序的目标需要依赖于编译的目标。`target` 需要指定`name` 以使Ant能够找到并执行对应目标。在这一次的使用中，主要用到了Ant 的 `javac` 命令来将java源文件编译为class文件, `jar` 命令来将class文件生成为jar文件, `java` 命令来运行生成的 jar 文件, `junit` 命令来通过Junit来对编写的程序进行测试, `mkdir` 命令来创建文件夹，`delete` 命令来实现 `ant clean` 目标以清理编译生成的文件。

### Junit

Junit的使用需要通过编写测试类来进行，对于测试类中的测试方法需要添加 `@Test` Annotation，方法中通过调用`assertEquals()` 方法来检查结果是否正确。测试类编写后在Ant中使用 junit 命令，指定`classpath` 为 junit.jar 和 hamcrest-core.jar以及在`batchtest` 中指定被测试的类，将这一测试的目标命名为`test` 并指定其依赖于编译目标后就可以在命令行下通过`ant test` 来简单地执行junit测试了。