package darts

import "math"

func Score(x, y float64) int {
	x2, y2 := math.Abs(x), math.Abs(y)

	if x2 < 0.8 && y2 <= 1 {
		return 10
	}

	if x2 <= 3.5 && y2 <= 5 {
		return 5
	}

	if x2 <= 5 && y2 == 0 {
		return 5
	}

	if x2 <= 7 && y2 <= 10 {
		return 1
	}

	return 0
}
