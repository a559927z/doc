package main

import "fmt"

// 在函数中词法域可以深度嵌套，因此内部的一个声明可能屏蔽外部的声明。还有许多语法块是 if 或 for 等控制流语句构造的。下面的代码有三个不同的变量 x，因为它们是定义在不同的词法域（这个例子只是为了演示作用域规则，但不是好的编程风格）。
func main() {
	x := "hello!"
	for i := 0; i < len(x); i++ {
		x := x[i]
		if x != '!' {
			x := x + 'A' - 'a'
			fmt.Printf("%c", x) // "HELLO" (每次迭代一个字母)
		}
	}
}
