package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

// totalSum [] saves total sums of each Yn
var totalSum []int

// totalIndex traces each element in totalSum[]
var totalIndex int = 0

// Yn [] saves each Yn number
var Yn []int

func main() {

	var N int

	fmt.Scanf("%d\n", &N)
	if N < 0 || N > 100 {
		fmt.Println("Type a number following the rule: 1 <= N <= 100")
		return
	}

	totalSum = make([]int, N, N)

	if totalTest(N) == 0 {
		// Print totalSum[] line by line
		printAll(0, N)
	}
}

func printAll(i int, N int) int {
	if i == N {
		return 0
	}

	fmt.Println(totalSum[i])
	return printAll(i+1, N)
}

func totalTest(N int) int {

	if N == 0 {
		return 0
	}

	var X int
	fmt.Scanf("%d\n", &X)

	if X <= 0 || X > 100 {
		fmt.Println("Type a number following the rule: 0 < X <= 100")
		os.Exit(1)
	}
	// Take a string argument
	var YnString string

	scanner := bufio.NewScanner(os.Stdin)
	if scanner.Scan() {
		YnString = scanner.Text()
	}

	// Delimit the string argument by space
	YnStringSlice := strings.Fields(YnString)

	// Make X length of slice
	Yn = make([]int, X, X)
	// put integers to the slice
	eachTest(0, YnStringSlice)

	// Add up all elements in Yn
	sum(0, X)
	totalIndex++

	return totalTest(N - 1)

}

// Put Yn number to Yn (global variable)
func eachTest(i int, YnStringSlice []string) int {

	if i == len(Yn) {
		return 0
	}
	var e int
	e, _ = strconv.Atoi(YnStringSlice[i])

	if e < -100 || e > 100 {
		fmt.Println("Type a number following the rule: -100 <= Yn <= 100")
		os.Exit(1)
	}

	Yn[i] = e

	return eachTest(i+1, YnStringSlice)
}

func sum(i int, X int) int {
	if i == X {
		return 0
	}

	if totalSum[totalIndex] != 0 {
		if Yn[i] > 0 {
			totalSum[totalIndex] += (Yn[i] * Yn[i])
		}
	} else {
		if Yn[i] > 0 {
			totalSum[totalIndex] = 0
			totalSum[totalIndex] += (Yn[i] * Yn[i])
		}
	}

	return sum(i+1, X)
}
