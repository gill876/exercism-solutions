import java.util.List;
import java.util.stream.Collectors;

class Knapsack {
    int maximumValue(int capacity, List<Item> items) {
        var elegibleItems = items.stream()
            .filter(item -> item.weight <= capacity)
            .toList();

        return knapsack(capacity, elegibleItems);
    }

    private static int knapsack(int capacity, List<Item> items) {
        int itemsLength = items.size();
        int[][] cache = new int[itemsLength + 1][capacity + 1];

        for (int i = 1; i <= itemsLength; i++)
            for (int j = 1; j <= capacity; j++)
                cache[i][j] = -1;

        return knapsackRecursive(capacity, items, itemsLength, cache);
    }

    private static int knapsackRecursive(int capacity, List<Item> items, int n, int[][] cache) {
        if (cache[n][capacity] != -1)
            return cache[n][capacity];

        Item current = items.get(n - 1);

        int skipValue = knapsackRecursive(capacity, items, n - 1, cache);
    
        int pickValue = (current.weight <= capacity)
            ? current.value + knapsackRecursive(capacity - current.weight,
                                                items,
                                                n - 1,
                                                cache)
            : 0;

        return (cache[n][capacity] = Math.max(pickValue, skipValue));
    }

}
