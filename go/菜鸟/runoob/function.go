package runoob

import "fmt"

func swap(x, y string) (string, string) {
	return y, x
}

/* 函数返回两个数的最大值 */
func max(num1, num2 int) int {
	/* 定义局部变量 */
	var result int

	if num1 > num2 {
		result = num1
	} else {
		result = num2
	}
	return result
}

func testFor2() {
	var a int = 0
	fmt.Println("for start")
	for a := 0; a < 10; a++ {
		fmt.Println(a)
	}
	fmt.Println("for end")
	fmt.Println(a)
}
func testFor() {
	var a int = 0
	fmt.Println("for start")
	for a = 0; a < 10; a++ {
		fmt.Println(a)
	}
	fmt.Println("for end")
	fmt.Println(a)
}

func main() {
	a, b := swap("Google", "Runoob")
	fmt.Println(a, b)

	/* 定义局部变量 */
	var aa int = 100
	var bb int = 200
	var ret int

	/* 调用函数并返回最大值 */
	ret = max(aa, bb)

	fmt.Printf("最大值是 : %d\n", ret)
	testFor2()
	testFor()
}
