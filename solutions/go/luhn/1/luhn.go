package luhn

import (
	"strconv"
	"strings"
)

func Valid(id string) bool {
	trimmed := strings.Join(strings.Fields(id), "")
	_, err := strconv.ParseFloat(trimmed, 64)

	if len(trimmed) <= 1 || err != nil {
		return false
	}

	sum := 0
	j := 1
	for i := len(trimmed) - 1; i >= 0; i-- {
		c := int(trimmed[i] - '0')
		if j%2 == 0 {

			prod := c * 2

			if prod > 9 {
				sum += prod - 9
			} else {
				sum += prod
			}
		} else {
			sum += c
		}
		j++
	}

	return sum%10 == 0
}
