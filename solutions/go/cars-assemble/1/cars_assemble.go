package cars

const (
	TEN_CARS_BATCH_PRODUCTION_COST = 95_000
	ONE_CAR_PRODUCTION_COST        = 10_000
)

// CalculateWorkingCarsPerHour calculates how many working cars are
// produced by the assembly line every hour.
func CalculateWorkingCarsPerHour(productionRate int, successRate float64) float64 {
	return float64(productionRate) * (successRate / float64(100))
}

// CalculateWorkingCarsPerMinute calculates how many working cars are
// produced by the assembly line every minute.
func CalculateWorkingCarsPerMinute(productionRate int, successRate float64) int {
	return int(CalculateWorkingCarsPerHour(productionRate, successRate) / float64(60))
}

// CalculateCost works out the cost of producing the given number of cars.
func CalculateCost(carsCount int) uint {
	batch_quotient := carsCount / 10
	batch_remainder := carsCount % 10

	return uint(TEN_CARS_BATCH_PRODUCTION_COST*batch_quotient + batch_remainder*ONE_CAR_PRODUCTION_COST)
}
