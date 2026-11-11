import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class InterfazGrafica extends JFrame {

    private static final Color FONDO = new Color(245, 247, 251);
    private static final Color AZUL = new Color(47, 100, 222);
    private static final Color TEXTO = new Color(35, 45, 65);
    private static final Color SECUNDARIO = new Color(112, 124, 145);

    private final JComboBox<String> selector = new JComboBox<>(
            new String[]{"Circulo", "Rectangulo", "Triangulo"});
    private final JLabel[] etiquetasCampos = new JLabel[3];
    private final JTextField[] campos = new JTextField[3];
    private final JLabel areaValor = new JLabel("--");
    private final JLabel perimetroValor = new JLabel("--");
    private final JLabel extraTitulo = new JLabel(" ");
    private final JLabel extraValor = new JLabel(" ");
    private final JLabel estado = new JLabel("Ingresa las medidas para comenzar.");

    public InterfazGrafica() {
        super("Figuras | Calculadora geometrica");
        configurarEstilo();
        construirInterfaz();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(620, 560));
        setSize(760, 630);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configurarEstilo() {
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", TEXTO);
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout(0, 22));
        raiz.setBackground(FONDO);
        raiz.setBorder(BorderFactory.createEmptyBorder(30, 38, 34, 38));

        JPanel encabezado = new JPanel(new BorderLayout(0, 7));
        encabezado.setOpaque(false);
        JLabel titulo = new JLabel("Calculadora de figuras");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 27));
        titulo.setForeground(TEXTO);
        JLabel subtitulo = new JLabel("Selecciona una figura y calcula sus medidas.");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(SECUNDARIO);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(subtitulo, BorderLayout.CENTER);

        JPanel contenido = new JPanel(new GridLayout(1, 2, 20, 0));
        contenido.setOpaque(false);
        contenido.add(crearPanelEntrada());
        contenido.add(crearPanelResultados());

        raiz.add(encabezado, BorderLayout.NORTH);
        raiz.add(contenido, BorderLayout.CENTER);
        add(raiz);
    }

    private JPanel crearPanelEntrada() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 18));

        JLabel seccion = crearEtiqueta("FIGURA Y MEDIDAS", 11, true);
        JPanel parteSuperior = new JPanel(new BorderLayout(0, 9));
        parteSuperior.setOpaque(false);
        parteSuperior.add(seccion, BorderLayout.NORTH);
        selector.setFont(new Font("SansSerif", Font.PLAIN, 15));
        selector.setFocusable(false);
        parteSuperior.add(selector, BorderLayout.CENTER);

        JPanel formulario = new JPanel();
        formulario.setLayout(new javax.swing.BoxLayout(formulario, javax.swing.BoxLayout.Y_AXIS));
        formulario.setOpaque(false);
        for (int i = 0; i < campos.length; i++) {
            JPanel fila = new JPanel(new BorderLayout(0, 6));
            fila.setOpaque(false);
            etiquetasCampos[i] = crearEtiqueta("", 13, false);
            fila.setBorder(BorderFactory.createEmptyBorder(0, 0, 9, 0));
            campos[i] = new JTextField();
            ((AbstractDocument) campos[i].getDocument()).setDocumentFilter(new FiltroNumerico());
            campos[i].setFont(new Font("SansSerif", Font.PLAIN, 15));
            campos[i].setForeground(TEXTO);
            campos[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 226, 236)),
                    BorderFactory.createEmptyBorder(9, 10, 9, 10)));
            fila.add(etiquetasCampos[i], BorderLayout.NORTH);
            fila.add(campos[i], BorderLayout.CENTER);
            formulario.add(fila);
        }

        JButton calcular = new JButton("Calcular");
        calcular.setFont(new Font("SansSerif", Font.BOLD, 14));
        calcular.setForeground(Color.WHITE);
        calcular.setBackground(AZUL);
        calcular.setFocusPainted(false);
        calcular.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        calcular.addActionListener(evento -> calcular());

        JPanel inferior = new JPanel(new BorderLayout(0, 10));
        inferior.setOpaque(false);
        estado.setFont(new Font("SansSerif", Font.PLAIN, 12));
        estado.setForeground(SECUNDARIO);
        inferior.add(estado, BorderLayout.NORTH);
        inferior.add(calcular, BorderLayout.SOUTH);

        tarjeta.add(parteSuperior, BorderLayout.NORTH);
        tarjeta.add(formulario, BorderLayout.CENTER);
        tarjeta.add(inferior, BorderLayout.SOUTH);

        selector.addItemListener(evento -> {
            if (evento.getStateChange() == ItemEvent.SELECTED) {
                actualizarCampos();
            }
        });
        actualizarCampos();
        return tarjeta;
    }

    private JPanel crearPanelResultados() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 18));

        JPanel encabezado = new JPanel(new BorderLayout(0, 8));
        encabezado.setOpaque(false);
        encabezado.add(crearEtiqueta("RESULTADOS", 11, true), BorderLayout.NORTH);
        JLabel nota = new JLabel("Los valores se muestran con dos decimales.");
        nota.setFont(new Font("SansSerif", Font.PLAIN, 12));
        nota.setForeground(SECUNDARIO);
        encabezado.add(nota, BorderLayout.CENTER);

        JPanel valores = new JPanel(new GridLayout(3, 1, 0, 12));
        valores.setOpaque(false);
        valores.add(crearTarjetaValor("Area", areaValor));
        valores.add(crearTarjetaValor("Perimetro", perimetroValor));
        valores.add(crearTarjetaValor(extraTitulo, extraValor));

        JLabel pie = new JLabel("Basado en las formulas de cada figura.");
        pie.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pie.setForeground(SECUNDARIO);
        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(valores, BorderLayout.CENTER);
        tarjeta.add(pie, BorderLayout.SOUTH);
        return tarjeta;
    }

    private JPanel crearTarjetaValor(String nombre, JLabel valor) {
        return crearTarjetaValor(new JLabel(nombre), valor);
    }

    private JPanel crearTarjetaValor(JLabel nombre, JLabel valor) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(new Color(248, 250, 253));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(231, 235, 242)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        nombre.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nombre.setForeground(SECUNDARIO);
        valor.setFont(new Font("SansSerif", Font.BOLD, 22));
        valor.setForeground(TEXTO);
        panel.add(nombre, BorderLayout.NORTH);
        panel.add(valor, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(231, 235, 242)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return panel;
    }

    private JLabel crearEtiqueta(String texto, int tamano, boolean negrita) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", negrita ? Font.BOLD : Font.PLAIN, tamano));
        etiqueta.setForeground(negrita ? SECUNDARIO : TEXTO);
        return etiqueta;
    }

    private void actualizarCampos() {
        String figura = (String) selector.getSelectedItem();
        String[] nombres;
        if ("Rectangulo".equals(figura)) {
            nombres = new String[]{"Base", "Altura", ""};
        } else if ("Triangulo".equals(figura)) {
            nombres = new String[]{"Lado 1", "Lado 2", "Lado 3"};
        } else {
            nombres = new String[]{"Radio", "", ""};
        }
        for (int i = 0; i < campos.length; i++) {
            boolean visible = !nombres[i].isEmpty();
            etiquetasCampos[i].setText(nombres[i]);
            etiquetasCampos[i].getParent().setVisible(visible);
            if (!visible) {
                campos[i].setText("");
            }
        }
        extraTitulo.setText("Circulo".equals(figura) ? "Diametro" : "Numero de lados");
        extraValor.setText("--");
        areaValor.setText("--");
        perimetroValor.setText("--");
        estado.setText("Ingresa las medidas para comenzar.");
        revalidate();
        repaint();
    }

    private void calcular() {
        areaValor.setText("--");
        perimetroValor.setText("--");
        extraValor.setText("--");
        try {
            String figura = (String) selector.getSelectedItem();
            Figura resultado;
            double valorExtra;
            if ("Circulo".equals(figura)) {
                Circulo circulo = new Circulo(leerMedida(0));
                resultado = circulo;
                valorExtra = circulo.calcularDiametro();
            } else if ("Rectangulo".equals(figura)) {
                resultado = new Rectangulo(leerMedida(0), leerMedida(1));
                valorExtra = resultado.getLados();
            } else {
                double lado1 = leerMedida(0);
                double lado2 = leerMedida(1);
                double lado3 = leerMedida(2);
                if (lado1 + lado2 <= lado3 || lado1 + lado3 <= lado2
                        || lado2 + lado3 <= lado1) {
                    throw new IllegalArgumentException("Las medidas no forman un triangulo valido.");
                }
                resultado = new Triangulo(lado1, lado2, lado3);
                valorExtra = resultado.getLados();
            }
            double area = resultado.calcularArea();
            double perimetro = resultado.calcularPerimetro();
            if (!Double.isFinite(area) || !Double.isFinite(perimetro)
                    || !Double.isFinite(valorExtra)) {
                throw new IllegalArgumentException("Las medidas son demasiado grandes para calcular.");
            }
            areaValor.setText(formatear(area));
            perimetroValor.setText(formatear(perimetro));
            extraValor.setText("Circulo".equals(figura)
                    ? formatear(valorExtra) : String.valueOf((int) valorExtra));
            estado.setForeground(new Color(38, 139, 91));
            estado.setText("Calculo realizado correctamente.");
        } catch (IllegalArgumentException error) {
            estado.setForeground(new Color(190, 55, 55));
            estado.setText(error.getMessage());
        }
    }

    private double leerMedida(int indice) {
        String texto = campos[indice].getText().trim().replace(',', '.');
        double valor;
        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Ingresa un numero valido en cada medida.");
        }
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException("Las medidas deben ser numeros mayores que cero.");
        }
        return valor;
    }

    private String formatear(double valor) {
        return String.format(Locale.getDefault(), "%.2f", valor);
    }

    private static class FiltroNumerico extends DocumentFilter {

        @Override
        public void insertString(FilterBypass bypass, int offset, String texto,
                                 AttributeSet atributos) throws BadLocationException {
            replace(bypass, offset, 0, texto, atributos);
        }

        @Override
        public void replace(FilterBypass bypass, int offset, int longitud, String texto,
                            AttributeSet atributos) throws BadLocationException {
            String actual = bypass.getDocument().getText(0, bypass.getDocument().getLength());
            String nuevo = actual.substring(0, offset) + texto
                    + actual.substring(offset + longitud);
            if (nuevo.matches("\\d*(?:[.,]\\d*)?")) {
                bypass.replace(offset, longitud, texto, atributos);
            }
        }
    }
}
