import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class DiamondPrinter {
    List<String> printToList(char a) {
        int letter_position = Character.toLowerCase(a) - 'a' + 1;

        char top_right_diamond[][] = new char[letter_position][letter_position];

        for (int i = 1; i < letter_position + 1; i++) {
            for (int j = 1; j < letter_position + 1; j++) {
                if (i == j) {
                    top_right_diamond[i - 1][j - 1] = (char) (i + 64);
                } else {
                    top_right_diamond[i - 1][j - 1] = (char) ' ';
                }
            }
        }

        char[][] mirrored = new char[letter_position][letter_position];

        for (int i = 0; i < letter_position; i++) {
            mirrored[i] = top_right_diamond[letter_position - 1 - i].clone();
        }

        char joinedMatrix[][] = joinVertical(top_right_diamond, Arrays.copyOfRange(mirrored, 1, letter_position));

        int joinedMatrix_row_length = joinedMatrix.length;
        int joinedMatrix_col_length = joinedMatrix[0].length;
        char[][] joinedMatrix_mirrored = new char[joinedMatrix_row_length][joinedMatrix_col_length];

        for (int i = 0; i < joinedMatrix_row_length; i++) {
            for (int j = 0; j < joinedMatrix_col_length; j++) {
                joinedMatrix_mirrored[i][j] = joinedMatrix[i][joinedMatrix_col_length - 1 - j];
            }
        }

        int subset_col_length = joinedMatrix_col_length - 1;
        char[][] joinedMatrix_mirrored_subset = new char[joinedMatrix_row_length][subset_col_length];
        for (int i = 0; i < joinedMatrix_row_length; i++) {
            for (int j = 0; j < subset_col_length; j++) {
                joinedMatrix_mirrored_subset[i][j] = joinedMatrix_mirrored[i][j];
            }
        }

        char[][] final_matrix = joinHorizontal(joinedMatrix_mirrored_subset, joinedMatrix);

        List<String> result = new ArrayList<>();

        for (int i = 0; i < joinedMatrix_row_length; i++) {
            result.add(String.valueOf(final_matrix[i]));
        }

        return result;
    }

    public static char[][] joinVertical(char[][] matrix1, char[][] matrix2) {
        // Check if matrices have compatible sizes for vertical join
        if (matrix1.length > 0 && matrix2.length > 0 && matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException(
                    "Matrices must have the same number of columns to be joined vertically.");
        }

        int totalRows = matrix1.length + matrix2.length;
        // Create a new matrix with the combined number of rows
        char[][] result = new char[totalRows][];

        // Copy all rows from the first matrix
        System.arraycopy(matrix1, 0, result, 0, matrix1.length);

        // Copy all rows from the second matrix to the position right after the first
        // matrix's rows
        System.arraycopy(matrix2, 0, result, matrix1.length, matrix2.length);

        return result;
    }

    public static char[][] joinHorizontal(char[][] matrix1, char[][] matrix2) {
        // Check if matrices have compatible sizes for horizontal join
        if (matrix1.length != matrix2.length) {
            throw new IllegalArgumentException(
                    "Matrices must have the same number of rows to be joined horizontally.");
        }

        int rows = matrix1.length;
        int cols1 = (rows > 0) ? matrix1[0].length : 0;
        int cols2 = (rows > 0) ? matrix2[0].length : 0;
        int totalCols = cols1 + cols2;

        char[][] result = new char[rows][totalCols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix1[i], 0, result[i], 0, cols1);
            System.arraycopy(matrix2[i], 0, result[i], cols1, cols2);
        }

        return result;
    }
}
