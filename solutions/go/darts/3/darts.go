package darts

import "math"

func Score(x, y float64) int {
	score := math.Sqrt(x*x + y*y)

	switch {
	case score <= 1:
		return 10
	case score <= 5:
		return 5
	case score <= 10:
		return 1
	default:
		return 0
	}
}
