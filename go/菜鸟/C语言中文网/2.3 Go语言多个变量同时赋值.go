package main

import "fmt"

type IntSlice []int

func (p IntSlice) Len() int           { return len(p) }
func (p IntSlice) Less(i, j int) bool { return p[i] < p[j] }
func (p IntSlice) Swap(i, j int)      { p[i], p[j] = p[j], p[i] }

func main() {
	var a int = 100
	var b int = 200
	b, a = a, b
	fmt.Println(a, b)

}
