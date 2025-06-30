package src;

public class SistemaEcuaciones {

    // Resuelve sistema 2x2: ax + by = e, cx + dy = f
    public static double[] resolver2x2(double[][] coefs, double[] indep) {
        double a = coefs[0][0], b = coefs[0][1], c = coefs[1][0], d = coefs[1][1];
        double e = indep[0], f = indep[1];
        double det = a * d - b * c;
        if (det == 0) throw new ArithmeticException("Sistema sin solución única");
        double x = (e * d - b * f) / det;
        double y = (a * f - e * c) / det;
        return new double[]{x, y};
    }

    // Resuelve sistema 3x3 usando regla de Cramer
    public static double[] resolver3x3(double[][] coefs, double[] indep) {
        double det = det3x3(coefs);
        if (det == 0) throw new ArithmeticException("Sistema sin solución única");
        double[][] m1 = reemplazarColumna(coefs, indep, 0);
        double[][] m2 = reemplazarColumna(coefs, indep, 1);
        double[][] m3 = reemplazarColumna(coefs, indep, 2);
        double x = det3x3(m1) / det;
        double y = det3x3(m2) / det;
        double z = det3x3(m3) / det;
        return new double[]{x, y, z};
    }

    private static double det3x3(double[][] m) {
        return m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1])
             - m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0])
             + m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
    }

    private static double[][] reemplazarColumna(double[][] m, double[] col, int idx) {
        double[][] r = new double[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                r[i][j] = (j == idx) ? col[i] : m[i][j];
        return r;
    }
}