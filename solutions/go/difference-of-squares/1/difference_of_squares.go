package differenceofsquares

import "math"

func SquareOfSum(n int) int {
	n2 := float64(n)
	return int(math.Pow((n2*(n2+1))/2, 2))
}

func SumOfSquares(n int) int {
	return (n * (n + 1) * (2*n + 1)) / 6
}

func Difference(n int) int {
	return SquareOfSum(n) - SumOfSquares(n)
}
