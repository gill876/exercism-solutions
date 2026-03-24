class CollatzCalculator {

    int computeStepCount(int start) {

        if (start <= 0) {
            throw new IllegalArgumentException("Only positive integers are allowed");
        }

        int result = 0;

        int progress = start;
        while (true) {
          if (progress == 1)
            break;
    
          if (progress % 2 == 0) {
            progress = progress / 2;
          } else {
            progress = (progress * 3) + 1;
          }
    
          result += 1;
        }
    
        return result;
    }
}
