// Package weather provides tools to forecast the weather.
package weather

var (
	// CurrentCondition represents what weather condition is currently being experienced.
	CurrentCondition string

	// CurrentLocation represents where the current weather condition is happening.
	CurrentLocation string
)

// Forecast returns a string value equal to the current weather condition of a given city.
func Forecast(city, condition string) string {
	CurrentLocation, CurrentCondition = city, condition
	return CurrentLocation + " - current weather condition: " + CurrentCondition
}
