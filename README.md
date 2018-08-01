# regex-debugger
Java Regular Expression  backtracking debugger by Java agent

Java正则表达式回溯调试器,使用Java agent

java -javaagent:regex-debugger-0.0.1.jar -jar xxx.jar

```
// output as follows

Pattern$Branch.match$hack ==> 0
Pattern$BranchConn.match$hack ==> 4
Pattern$GroupTail.match$hack ==> 4
Pattern$Curly.match$hack ==> 7
Pattern$GroupTail.match$hack ==> 24
Pattern$Curly.match$hack ==> 24
Pattern$Ques.match$hack ==> 24
Pattern$Curly.match$hack ==> 25
Pattern$GroupTail.match$hack ==> 29
Pattern$Ques.match$hack ==> 29
Pattern$Curly.match$hack ==> 29
Pattern$GroupTail.match$hack ==> 29
Pattern$GroupTail.match$hack ==> 29
```
