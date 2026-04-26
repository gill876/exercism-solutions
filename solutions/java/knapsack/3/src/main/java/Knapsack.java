import java.util.List;
import java.util.stream.Collectors;

class Knapsack {

    int maximumValue(int maximumWeight, List<Item> items) {
        var filterOutHeavyItems = items.stream()
            .filter(item -> item.weight <= maximumWeight)
            .collect(Collectors.toList());

        if (filterOutHeavyItems.size() == 0)
            return 0;

        return knapsack(maximumWeight, filterOutHeavyItems);
    }

    private static int knapsack(int maximumWeight, List<Item> items) {
        int itemsLength = items.size();

        int[][] cache = new int[itemsLength + 1][maximumWeight + 1];

        for (int i = 1; i <= itemsLength; i++)
            for (int j = 1; j <= maximumWeight; j++)
                cache[i][j] = -1;

        return knapsackRecursive(maximumWeight, items, itemsLength, cache);
    }

    private static int knapsackRecursive(int maximumWeight, List<Item> items, int iterator, int[][] cache) {
        if (iterator == 0 || maximumWeight == 0)
            return 0;

        if (cache[iterator][maximumWeight] != -1)
            return cache[iterator][maximumWeight];

        int pickTreeValue = 0;

        if (items.get(iterator - 1).weight <= maximumWeight)
            pickTreeValue = items.get(iterator - 1).value
                    + knapsackRecursive(maximumWeight - items.get(iterator - 1).weight, items, iterator - 1, cache);

        int notPickTreeValue = knapsackRecursive(maximumWeight, items, iterator - 1, cache);

        return cache[iterator][maximumWeight] = Math.max(pickTreeValue, notPickTreeValue);
    }

}
