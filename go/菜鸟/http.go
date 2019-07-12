package main

import (
	"net/http"
)

// localhost:8080
func main() {
	http.Handle("/", http.FileServer(http.Dir(".")))
	http.ListenAndServe(":8080", nil)
}
