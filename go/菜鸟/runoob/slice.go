package runoob

import "fmt"

func slice() {
	/* 创建切片 */
	numbers := []int{0, 1, 2, 3, 4, 5, 6, 7, 8}
	printSlice(numbers)

	/* 打印原始切片 */
	fmt.Println("numbers ==", numbers)

	/* 打印子切片从索引1(包含) 到索引4(不包含)*/
	fmt.Println("numbers[1:4] ==", numbers[1:4])

	/* 默认下限为 0*/
	fmt.Println("numbers[:3] ==", numbers[:3])

	/* 默认上限为 len(s)*/
	fmt.Println("numbers[4:] ==", numbers[4:])

	// 或使用make()函数来创建切片:
	numbers1 := make([]int, 0, 5)
	printSlice(numbers1)

	/* 打印子切片从索引  0(包含) 到索引 2(不包含) */
	number2 := numbers[:2]
	printSlice(number2)

	/* 打印子切片从索引 2(包含) 到索引 5(不包含) */
	number3 := numbers[2:5]
	printSlice(number3)
}

func appendCopy() {
	var numbers []int
	printSlice(numbers)

	/* 允许追加空切片 */
	numbers = append(numbers, 0)
	printSlice(numbers)

	/* 向切片添加一个元素 */
	numbers = append(numbers, 1)
	printSlice(numbers)

	/* 同时添加多个元素 */
	numbers = append(numbers, 2, 3, 4)
	printSlice(numbers)

	/* 创建切片 numbers1 是之前切片的两倍容量*/
	numbers1 := make([]int, len(numbers), (cap(numbers))*2)

	/* 拷贝 numbers 的内容到 numbers1 */
	copy(numbers1, numbers)
	printSlice(numbers1)
}

func printSlice(x []int) {
	// 切片是可索引的，并且可以由 len() 方法获取长度。
	// 切片提供了计算容量的方法 cap() 可以测量切片最长可以达到多少。
	fmt.Printf("len=%d cap=%d slice=%v\n", len(x), cap(x), x)
}

/**
 * 我们可以看出切片，实际的是获取数组的某一部分，len切片<=cap切片<=len数组，切片由三部分组成：指向底层数组的指针、len、cap。
 */
func test1() {
	numbers := []int{0, 1, 2, 3, 4, 5, 6, 7, 8}
	printSlice(numbers)
	fmt.Println("numbers ==", numbers)
	numbers1 := numbers[1:4]
	printSlice(numbers1)
	fmt.Println("numbers[:3] ==", numbers[:3])
	numbers2 := numbers[4:]
	printSlice(numbers2)
	numbers3 := make([]int, 0, 5)
	printSlice(numbers3)
	numbers4 := numbers[:2]
	printSlice(numbers4)
}

// sliceTest 函数是切片测试代码，简单的两种初始化方式。
//
//twoDimensionsArray 函数是二维数组测试函数。
//
//测试结果:
//
//1.二维数组中的元素(一位数组)个数 > 限制的列数,异常;
//
//2.二维数组中的元素(一位数组)个数 <= 限制的列数,正常;
//
//假设列数为 3, 某个一位数组 {1}, 则后两个元素,默认为 0。
func sliceTest() {
	arr := []int{1, 2, 3, 4, 5}
	s := arr[:]
	for e := range s {
		fmt.Println(s[e])
	}
	s1 := make([]int, 3)
	for e := range s1 {
		fmt.Println(s1[e])
	}
}

func twoDimensionArray() {
	/* 数组 - 5 行 2 列*/
	var a = [][]int{{0, 0}, {1, 2}, {2}, {3, 6}, {4, 8}}
	var i, j int

	/* 输出数组元素 */
	for i = 0; i < len(a); i++ {
		for j = 0; j < len(a[i]); j++ {
			fmt.Printf("a[%d][%d] = %d\n", i, j, a[i][j])
		}
	}
}

// 合并多个数组
func test2() {
	var arr1 = []int{1, 2, 3}
	var arr2 = []int{4, 5, 6}
	var arr3 = []int{7, 8, 9}
	var s1 = append(append(arr1, arr2...), arr3...)
	fmt.Printf("s1: %v\n", s1)
}

// 在做函数调用时，slice 按引用传递，array 按值传递
func changeSliceTest() {
	arr1 := []int{1, 2, 3}
	arr2 := [3]int{1, 2, 3}
	arr3 := [3]int{1, 2, 3}

	fmt.Println("before change arr1, ", arr1)
	changeSlice(arr1) // slice 按引用传递
	fmt.Println("after change arr1, ", arr1)

	fmt.Println("before change arr2, ", arr2)
	changeArray(arr2) // array 按值传递
	fmt.Println("after change arr2, ", arr2)

	fmt.Println("before change arr3, ", arr3)
	changeArrayByPointer(&arr3) // 可以显式取array的 指针
	fmt.Println("after change arr3, ", arr3)
}

func changeSlice(arr []int) {
	arr[0] = 9999
}

func changeArray(arr [3]int) {
	arr[0] = 6666
}

func changeArrayByPointer(arr *[3]int) {
	arr[0] = 6666
}

func main() {
	slice()
	fmt.Println("======================")
	appendCopy()
	fmt.Println("======================")
	test1()
	fmt.Println("======================")
	sliceTest()
	twoDimensionArray()
	fmt.Println("======================")
	test2()
	fmt.Println("======================")
	changeSliceTest()
}
