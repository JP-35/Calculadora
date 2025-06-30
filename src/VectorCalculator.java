package src;

public class VectorCalculator {

    public double[] sumar(double[] a, double[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Dimensiones diferentes");
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) res[i] = a[i] + b[i];
        return res;
    }

    public double[] restar(double[] a, double[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Dimensiones diferentes");
        double[] res = new double[a.length];
        for (int i = 0; i < a.length; i++) res[i] = a[i] - b[i];
        return res;
    }

    public double[] multiplicarPorEscalar(double[] v, double escalar) {
        double[] res = new double[v.length];
        for (int i = 0; i < v.length; i++) res[i] = v[i] * escalar;
        return res;
    }

    public double productoEscalar(double[] a, double[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Dimensiones diferentes");
        double res = 0;
        for (int i = 0; i < a.length; i++) res += a[i] * b[i];
        return res;
    }

    public double[] productoVectorial(double[] a, double[] b) {
        if (a.length != 3 || b.length != 3) throw new IllegalArgumentException("Solo definido para 3D");
        return new double[]{
            a[1] * b[2] - a[2] * b[1],
            a[2] * b[0] - a[0] * b[2],
            a[0] * b[1] - a[1] * b[0]
        };
    }

    public static String vectorToString(double[] v) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
            if (i < v.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}