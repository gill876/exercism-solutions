package isbnverifier

func IsValidISBN(isbn string) bool {
	if len(isbn) < 10 {
		return false
	}

	sum, n := 0, 10

	for pos := range isbn {
		i := isbn[pos]

		if n <= 0 {
			n = 1
		}

		if i == 'X' && n == 1 {
			sum += 10 * n
		} else if i == 'X' {
			n--
		} else {
			byte_to_int := int(i - '0')

			if byte_to_int == 253 {
				continue
			} else if byte_to_int > 9 {
				return false
			}

			sum += byte_to_int * n
			n--
		}
	}

	return sum%11 == 0
}
