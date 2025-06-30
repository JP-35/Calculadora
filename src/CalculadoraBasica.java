package src;

public class CalculadoraBasica {
    private double ultimoResultado = 0;

    public double suma(double a, double b) {
        ultimoResultado = a + b;
        return ultimoResultado;
    }

    public double resta(double a, double b) {
        ultimoResultado = a - b;
        return ultimoResultado;
    }

    public double multiplicacion(double a, double b) {
        ultimoResultado = a * b;
        return ultimoResultado;
    }

    public double division(double a, double b) {
        if (b == 0) throw new ArithmeticException("División por cero");
        ultimoResultado = a / b;
        return ultimoResultado;
    }

    public double potencia(double base, double exponente) {
        ultimoResultado = Math.pow(base, exponente);
        return ultimoResultado;
    }

    public double raiz(double base, double indice) {
        ultimoResultado = Math.pow(base, 1.0 / indice);
        return ultimoResultado;
    }

    public double getUltimoResultado() {
        return ultimoResultado;
    }
}