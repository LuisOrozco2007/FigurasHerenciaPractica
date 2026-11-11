public class Main {

    public static void main(String[] args) {
        SistemaCLI sistema = new SistemaCLI();
        int opcion;

        do {
            opcion = sistema.menu();

            switch (opcion) {
                case 1:
                    sistema.circulo();
                    break;
                case 2:
                    sistema.rectangulo();
                    break;
                case 3:
                    sistema.triangulo();
                    break;
                case 4:
                    sistema.salir();
                    break;
                default:
                    System.out.println("Opcion incorrecta.");
                    break;
            }
        } while (opcion != 4);
    }
}