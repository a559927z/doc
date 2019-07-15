package runoob

import "fmt"

const (
	a = 1 << 3
	i = 1 << iota
	j = 1 << iota
	k
	l
)

func main() {
	fmt.Println("a=", a)
	fmt.Println("i=", i)
	fmt.Println("j=", j)
	fmt.Println("k=", k)
	fmt.Println("l=", l)
}
