package main

import (
	"fmt"
	"time"
)

func Chann(ch chan int, stopCh chan bool) {
	var i int
	i = 10
	for j := 0; j < 10; j++ {
		ch <- i
		time.Sleep(time.Second)
	}
	stopCh <- true
}

func main() {
	ch := make(chan int)
	c := 0
	stopCh := make(chan bool)

	for {
		select {
		case c = <-ch:
			fmt.Println("Recvice", c)
			fmt.Println("channel")
		case s := <-ch:
			fmt.Println("Recvice", s)
		case _ = <-stopCh:
			goto end
		}
	}
end:
}
