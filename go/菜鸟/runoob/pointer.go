package main

import "fmt"

func pointerPointer() {
	var a int = 1

	var ptr1 *int = &a
	var ptr2 **int = &ptr1
	var ptr3 **(*int) = &ptr2 // 也可以写作：var ptr3 ***int = &ptr2
	// 依次类推
	fmt.Println("a:", a)
	fmt.Println("ptr1", ptr1)
	fmt.Println("ptr2", ptr2)
	fmt.Println("ptr3", ptr3)
	fmt.Println("*ptr1", *ptr1)
	fmt.Println("**ptr2", **ptr2)
	fmt.Println("**(*ptr3)", **(*ptr3)) // 也可以写作：***ptr3
}

func main() {
	pointerPointer()

	fmt.Println("==================")

	var a int = 10
	var ip *int
	fmt.Printf("变量的地址：%x\n", &a)
	fmt.Println("变量的地址：", &a)
	ip = &a
	fmt.Println("ip 变量存储的指针地址:", ip)
	fmt.Println("ip 变量存储的指针地址的值:", *ip)
	fmt.Println("ip 变量存储的指针地址的地址:", &ip)
	var ptr *int
	if ptr != nil {
		if ip != nil {
			fmt.Println("ptr不是空指针")
			fmt.Println("ip不是空指针")
		} else {
			fmt.Println("ptr不是空指针")
			fmt.Println("ip是空指针")
		}
	} else {
		if ip != nil {
			fmt.Println("ptr是空指针")
			fmt.Println("ip不是空指针")
		} else {
			fmt.Println("ptr是空指针")
			fmt.Println("ip是空指针")
		}
	}
	/*  自学的时候想到能不能使用 switch 优化 for 繁琐的写法，但是发现 case 匹配到后会自动跳出 switch。
	    查了一下 select 等方法发现并不适用， 最后发现了 fallthrough 可以很好的用在这里（不过要注意 fallthrough 存在的位置，避免产生逻辑混乱）  */
	switch {
	case ptr != nil:
		fmt.Println("ptr不是空指针")
		fallthrough
	case ptr == nil:
		fmt.Println("ptr是空指针")
		fallthrough
	case ip != nil:
		fmt.Println("ip不是空指针")
	default:
		fmt.Println("ip是空指针")
	}
}
