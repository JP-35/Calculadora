package src;

public class MatrixCalculator {

    public double[][] sumar(double[][] a, double[][] b) {
        checkSameSize(a, b);
        int n = a.length, m = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res[i][j] = a[i][j] + b[i][j];
        return res;
    }

    public double[][] restar(double[][] a, double[][] b) {
        checkSameSize(a, b);
        int n = a.length, m = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res[i][j] = a[i][j] - b[i][j];
        return res;
    }

    public double[][] multiplicarPorEscalar(double[][] mat, double esc) {
        int n = mat.length, m = mat[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res[i][j] = mat[i][j] * esc;
        return res;
    }

    public double[][] multiplicar(double[][] a, double[][] b) {
        if (a[0].length != b.length)
            throw new IllegalArgumentException("Dimensiones incompatibles");
        int n = a.length, m = b[0].length, p = a[0].length;
        double[][] res = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < p; k++)
                    res[i][j] += a[i][k] * b[k][j];
        return res;
    }

    public double determinante(double[][] mat) {
        if (mat.length != mat[0].length)
            throw new IllegalArgumentException("No cuadrada");
        return determinanteRec(mat);
    }

    private double determinanteRec(double[][] mat) {
        int n = mat.length;
        if (n == 1) return mat[0][0];
        if (n == 2) return mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
        double det = 0;
        for (int j = 0; j < n; j++) {
            det += Math.pow(-1, j) * mat[0][j] * determinanteRec(minor(mat, 0, j));
        }
        return det;
    }

    private double[][] minor(double[][] mat, int fila, int col) {
        int n = mat.length;
        double[][] res = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == fila) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                res[r][c++] = mat[i][j];
            }
            r++;
        }
        return res;
    }

    public double[][] transpuesta(double[][] mat) {
        int n = mat.length, m = mat[0].length;
        double[][] res = new double[m][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res[j][i] = mat[i][j];
        return res;
    }

    public double[][] inversa(double[][] mat) {
        int n = mat.length;
        if (n != mat[0].length)
            throw new IllegalArgumentException("No cuadrada");
        double det = determinante(mat);
        if (det == 0)
            throw new ArithmeticException("Matriz singular");
        double[][] adj = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                adj[j][i] = Math.pow(-1, i + j) * determinanteRec(minor(mat, i, j));
        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                inv[i][j] = adj[i][j] / det;
        return inv;
    }

    public double[][] dividir(double[][] a, double[][] b) {
        return multiplicar(a, inversa(b));
    }

    private void checkSameSize(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length)
            throw new IllegalArgumentException("Dimensiones diferentes");
    }

    public static String matrixToString(double[][] m) {
        if (m == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (double[] fila : m) {
            sb.append("[ ");
            for (double v : fila) sb.append(v).append(" ");
            sb.append("]\n");
        }
        return sb.toString();
    }
}