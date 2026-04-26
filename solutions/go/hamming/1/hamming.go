package hamming

import "errors"

func Distance(a, b string) (int, error) {
	distance := 0

	if len(a) != len(b) {
		return 0, errors.New("")
	}

	for i := range a {
		if a[i] != b[i] {
			distance += 1
		}
	}

	return distance, nil
}
