public class Rectangulo extends Figura {

    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        super("Rectangulo", 4);
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        area = base * altura;
        return area;
    }

    @Override
    public double calcularPerimetro() {
        perimetro = 2 * (base + altura);
        return perimetro;
    }
}
