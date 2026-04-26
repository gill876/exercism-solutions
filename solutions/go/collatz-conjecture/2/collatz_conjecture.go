package collatzconjecture

import "errors"

func CollatzConjecture(n int) (int, error) {
	if n < 1 {
		return 0, errors.New("n must be greater than 0")
	}

	n2 := n
	i := 0
	for n2 != 1 {
		if n2%2 == 0 {
			n2 /= 2
		} else {
			n2 = n2*3 + 1
		}
		i++
	}

	return i, nil
}
