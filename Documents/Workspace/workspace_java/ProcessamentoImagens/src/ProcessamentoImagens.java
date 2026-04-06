import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Esta classe configura a interface gráfica usando Java Swing,
 * preparando todos os menus e painéis para a implementação dos algoritmos.
 */
public class ProcessamentoImagens extends JFrame {

    // Componentes para exibir as imagens
    private JLabel lblImagemOriginal;
    private JLabel lblImagemTransformada;
    
    // Variáveis para armazenar as imagens em memória
    private BufferedImage imgOriginal;
    private BufferedImage imgTransformada;
    private BufferedImage imgOriginalSalva;
    private java.util.Deque<BufferedImage> historicoOriginal = new java.util.ArrayDeque<>();

    // Botões para auxilio na manipulação das imagens 
    private JButton btnAplicar;
    private JButton btnOriginal;
    private JButton btnHistorico;

    public ProcessamentoImagens() {
        // Configurações básicas da janela principal
        setTitle("Sistema de Processamento Digital de Imagens");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar no ecrã
        setLayout(new BorderLayout());

        // 1. Painel Superior (Nome do Autor)
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(230, 230, 250));
        JLabel lblAutores = new JLabel("Autor: Patrick Andrei Pinheiro de Lemos"); 
        lblAutores.setFont(new Font("Arial", Font.BOLD, 16));
        painelTopo.add(lblAutores);
        add(painelTopo, BorderLayout.NORTH);

        // 2. Painel Central (Duas Janelas para Imagens)
        JPanel painelImagens = new JPanel(new GridLayout(1, 2, 10, 10));
        painelImagens.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel dos botões
        btnAplicar = new JButton("Usar como Original");
        btnAplicar.setVisible(false);
        btnAplicar.addActionListener(e -> aplicarTransformadaComoOriginal());
        
        btnOriginal = new JButton("Voltar à imagem original");
        btnOriginal.setVisible(false);
        btnOriginal.addActionListener(e -> RetornaImagemOriginal());

        btnHistorico = new JButton("Voltar à última imagem");
        btnHistorico.setVisible(false);
        btnHistorico.addActionListener(e -> RetornaUltimaImagem());
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnHistorico);
        painelBotoes.add(btnOriginal);
        painelBotoes.add(btnAplicar);
        add(painelBotoes, BorderLayout.SOUTH); // apenas UM add no SOUTH

        // Configuração da área da Imagem Original
        JPanel painelEsq = new JPanel(new BorderLayout());
        painelEsq.setBorder(BorderFactory.createTitledBorder("Imagem Original"));
        lblImagemOriginal = new JLabel("Nenhuma imagem carregada", SwingConstants.CENTER);
        painelEsq.add(new JScrollPane(lblImagemOriginal), BorderLayout.CENTER);

        // Configuração da área da Imagem Transformada
        JPanel painelDir = new JPanel(new BorderLayout());
        painelDir.setBorder(BorderFactory.createTitledBorder("Imagem Transformada"));
        lblImagemTransformada = new JLabel("Aguardando transformação...", SwingConstants.CENTER);
        painelDir.add(new JScrollPane(lblImagemTransformada), BorderLayout.CENTER);

        painelImagens.add(painelEsq);
        painelImagens.add(painelDir);
        add(painelImagens, BorderLayout.CENTER);

        // 3. Criação da Barra de Menus
        criarMenus();
    }

    private void aplicarTransformadaComoOriginal() {
        if (imgTransformada == null) return;
    
        historicoOriginal.push(imgOriginal); // emplilha o estado atual
        imgOriginal = imgTransformada;
        imgTransformada = null;
    
        lblImagemOriginal.setIcon(new ImageIcon(imgOriginal));
        lblImagemOriginal.setText("");
    
        lblImagemTransformada.setIcon(null);
        lblImagemTransformada.setText("Aguardando transformação...");
    
        btnAplicar.setVisible(false); // Esconde o botão novamente
        // Trás os botões da manipulação
        btnOriginal.setVisible(true); 
        btnHistorico.setVisible(true);
    }

    private void RetornaImagemOriginal() {

        imgOriginal = imgOriginalSalva;
        imgTransformada = null;
    
        lblImagemOriginal.setIcon(new ImageIcon(imgOriginal));
        lblImagemOriginal.setText("");
    
        lblImagemTransformada.setIcon(null);
        lblImagemTransformada.setText("Aguardando transformação...");

        // Esconde os botões novamente
        btnOriginal.setVisible(false); 
        btnHistorico.setVisible(false );
        btnAplicar.setVisible(false);
    }

    private void RetornaUltimaImagem() {

        if (!historicoOriginal.isEmpty()) {
            imgOriginal = historicoOriginal.pop(); // desempilha o estado anterior

            imgTransformada = null;

            lblImagemOriginal.setIcon(new ImageIcon(imgOriginal));
            lblImagemOriginal.setText("");
        
            lblImagemTransformada.setIcon(null);
            lblImagemTransformada.setText("Aguardando transformação...");

            if (historicoOriginal.isEmpty()) {
                btnOriginal.setVisible(false); // Esconde o botão novamente
                btnHistorico.setVisible(false );
            }
        }
    }


    /**
     * Método responsável por construir a barra de menus e os seus itens.
     */
    private void criarMenus() {
        JMenuBar menuBar = new JMenuBar();

        // --- MENU: ARQUIVO ---
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemAbrir = new JMenuItem("Abrir imagem");
        JMenuItem itemSalvar = new JMenuItem("Salvar imagem");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        JMenuItem itemSair = new JMenuItem("Sair");

        itemAbrir.addActionListener(e -> abrirImagem());
        itemSalvar.addActionListener(e -> salvarImagem());
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Matricula: 0403027\nNome: Patrick Andrei Pinheiro de Lemos\nProjeto: Sistema de Processamento de Imagens, desenvolvido para a disciplina de PDI."));
        itemSair.addActionListener(e -> System.exit(0));

        menuArquivo.add(itemAbrir);
        menuArquivo.add(itemSalvar);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSobre);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSair);

        // --- MENU: TRANSFORMAÇÕES GEOMÉTRICAS ---
        JMenu menuGeo = new JMenu("Transformações Geométricas");
        String[] opsGeo = {"Transladar", "Rotacionar", "Espelhar", "Ampliar", "Reduzir"};
        for (String op : opsGeo) {
            JMenuItem item = new JMenuItem(op);
            item.addActionListener(e -> ProcessaImagem(op));
            menuGeo.add(item);
        }

        // --- MENU: FILTROS ---
        JMenu menuFiltros = new JMenu("Filtros");
        String[] opsFiltros = {"Grayscale", "Brilho", "Contraste", "Passa Baixa", "Passa Alta", "Threshold"};
        for (String op : opsFiltros) {
            JMenuItem item = new JMenuItem(op);
            item.addActionListener(e -> ProcessaImagem(op));
            menuFiltros.add(item);
        }

        // --- MENU: MORFOLOGIA MATEMÁTICA ---
        JMenu menuMorfo = new JMenu("Morfologia Matemática");
        String[] opsMorfo = {"Dilatação", "Erosão", "Abertura", "Fechamento", "Afinamento"};
        for (String op : opsMorfo) {
            JMenuItem item = new JMenuItem(op);
            item.addActionListener(e -> ProcessaImagem(op));
            menuMorfo.add(item);
        }

        // --- MENU: EXTRAÇÃO DE CARACTERÍSTICAS ---
        JMenu menuExtracao = new JMenu("Extração de Características");
        JMenuItem itemDesafio = new JMenuItem("DESAFIO");
        //itemDesafio.addActionListener(e -> simularProcessamento("DESAFIO (Extração)"));
        menuExtracao.add(itemDesafio);

        // Adicionar menus à barra
        menuBar.add(menuArquivo);
        menuBar.add(menuGeo);
        menuBar.add(menuFiltros);
        menuBar.add(menuMorfo);
        menuBar.add(menuExtracao);

        setJMenuBar(menuBar);
    }

    /**
     * Lógica para abrir uma imagem do disco.
     */
    private void abrirImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione uma imagem");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG, BMP)", "jpg", "jpeg", "png", "bmp"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File ficheiro = fileChooser.getSelectedFile();
                imgOriginal = ImageIO.read(ficheiro);
                imgOriginalSalva = imgOriginal;
                imgTransformada = null; // Limpa a transformação anterior

                // Atualiza a interface (usamos ImageIcon para exibir a BufferedImage no JLabel)
                lblImagemOriginal.setIcon(new ImageIcon(imgOriginal));
                lblImagemOriginal.setText(""); // Remove o texto
                lblImagemTransformada.setIcon(null);
                lblImagemTransformada.setText("Aguardando transformação...");
                
                // Redesenha a tela
                pack(); 
                setSize(1200, 700); // Mantém o tamanho base
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir a imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lógica para salvar a imagem transformada no disco.
     */
    private void salvarImagem() {
        if (imgTransformada == null) {
            JOptionPane.showMessageDialog(this, "Não há imagem transformada para salvar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Imagem Transformada");

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File ficheiro = fileChooser.getSelectedFile();
                // Garante que tenha extensão .png se não houver
                if(!ficheiro.getName().toLowerCase().endsWith(".png")) {
                    ficheiro = new File(ficheiro.getAbsolutePath() + ".png");
                }
                ImageIO.write(imgTransformada, "png", ficheiro);
                JOptionPane.showMessageDialog(this, "Imagem salva com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ProcessaImagem(String tecnica){
        if(imgOriginal == null){
            JOptionPane.showMessageDialog(
                this,
                "Abra uma imagem primeiro",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Usar switch é a melhor forma de comparar Strings em Java a partir do Java 7
        switch (tecnica) {
            case "Transladar":
                JTextField txtX = new JTextField(5);
                JTextField txtY = new JTextField(5);
                
                JPanel painelTranslacao = new JPanel();
                painelTranslacao.add(new JLabel("Eixo X:"));
                painelTranslacao.add(txtX);
                painelTranslacao.add(Box.createHorizontalStrut(15));
                painelTranslacao.add(new JLabel("Eixo Y:"));
                painelTranslacao.add(txtY);
                
                int result = JOptionPane.showConfirmDialog(this, painelTranslacao, "Digite os valores de Translação", JOptionPane.OK_CANCEL_OPTION);
                
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int tx = Integer.parseInt(txtX.getText());
                        int ty = Integer.parseInt(txtY.getText());
                        TransladarImagem(imgOriginal, tx, ty);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite apenas números inteiros válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Rotacionar":
                String inputAngulo = JOptionPane.showInputDialog(this, "Digite o ângulo de rotação (em graus):");
                if (inputAngulo != null && !inputAngulo.trim().isEmpty()) { 
                    try {
                        double angulo = Double.parseDouble(inputAngulo);
                        RotacionarImagem(imgOriginal, angulo);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite um número válido (ex: 45 ou 45.5).", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Espelhar":
                String[] opcoes = {"Horizontal", "Vertical"};
                int escolha = JOptionPane.showOptionDialog(this, 
                        "Escolha o tipo de espelhamento:", "Espelhar Imagem",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                        null, opcoes, opcoes);
                
                if (escolha != JOptionPane.CLOSED_OPTION) {
                    boolean espelharHorizontal = (escolha == 0);
                    EspelharImagem(imgOriginal, espelharHorizontal);
                }
                break;
            case "Ampliar": 
            case "Reduzir":
                String acao = tecnica.equals("Ampliar") ? "aumentar" : "diminuir";
                String inputEscala = JOptionPane.showInputDialog(this, "Digite o fator de escala (ex: 1.5 para " + acao + "):");
                if (inputEscala != null && !inputEscala.trim().isEmpty()) {
                    try {
                        double fator = Double.parseDouble(inputEscala);
                        AmpliarReduzirImagem(imgOriginal, fator);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Grayscale":
                /*JTextField cont = new JTextField(5);
                JTextField bril = new JTextField(5);
                
                JPanel painelGrayscale = new JPanel();
                painelGrayscale.add(new JLabel("Contraste:"));
                painelGrayscale.add(cont);
                painelGrayscale.add(Box.createHorizontalStrut(15));
                painelGrayscale.add(new JLabel("Brilho:"));
                painelGrayscale.add(bril);*/
                
                //float resultG = JOptionPane.showConfirmDialog(this, painelGrayscale, "Digite os valores de Translação", JOptionPane.OK_CANCEL_OPTION);
                
                /*if (resultG == JOptionPane.OK_OPTION) {
                    try {
                        double contraste = Double.parseDouble(cont.getText());
                        double brilho = Double.parseDouble(bril.getText());
                        GrayscaleImagem(imgOriginal, contraste, brilho);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite apenas números inteiros válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    }
                }*/
                GrayscaleImagem(imgOriginal);
                break;
            case "Brilho":
                String inputBrihlo = JOptionPane.showInputDialog(this, "Digite o fator de escala (ex: 1.5 para alterar o brilho):");
                if (inputBrihlo != null && !inputBrihlo.trim().isEmpty()) {
                    try {
                        double fator = Double.parseDouble(inputBrihlo);
                        BrilhoImagen(imgOriginal, fator);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Contraste":
                String inputContraste = JOptionPane.showInputDialog(this, "Digite o fator de escala (ex: 1.5 para alterar o contraste):");
                if (inputContraste != null && !inputContraste.trim().isEmpty()) {
                    try {
                        double fator = Double.parseDouble(inputContraste);
                        ContrasteImagen(imgOriginal, fator);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Passa Baixa":
            case "Passa Alta":
            case "Threshold":
            case "Dilatação":
            case "Erosão":
            case "Abertura":
            case "Fechamento":
            case "Afinamento":
            default:
                // Trata as chamadas dos próximos menus (Filtros, Morfologia)
                JOptionPane.showMessageDialog(this, "Técnica de " + tecnica + " será implementada em breve.");
                break;
        }
    }

    /**
     * Cria uma imagem em branco com as mesmas dimensões e tipo da imagem original.
     */
    private BufferedImage criarImagemEmBranco(BufferedImage original) {
        return new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
    }

    /**
     * Atualiza o painel direito com a imagem transformada calculada.
     */
    private void atualizarTelaComImagemTransformada() {
        lblImagemTransformada.setIcon(new ImageIcon(imgTransformada));
        lblImagemTransformada.setText("");
        btnAplicar.setVisible(true);
    }

    public void TransladarImagem(BufferedImage imgTransladar, int tx, int ty){
        /*
        Matriz de Translação (Agora padronizada com MAPEAMENTO REVERSO):
        Se o direto é: x' = x + tx
        O reverso é:   x  = x' - tx
        */
        
        int largura = imgTransladar.getWidth();
        int altura = imgTransladar.getHeight();
        
        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgTransladar);

        // 2. Loop usando MAPEAMENTO REVERSO (Varrendo a imagem de DESTINO)
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                
                // Descobre de onde vem o pixel original
                int x_original = x - tx;
                int y_original = y - ty;

                // 3. Verifica se a coordenada original existe
                if (x_original >= 0 && x_original < largura && y_original >= 0 && y_original < altura) {
                    int corOriginal = imgTransladar.getRGB(x_original, y_original);
                    imgTransformada.setRGB(x, y, corOriginal);
                } else {
                    // Preenche os "espaços vazios" deixados pela translação com branco
                    imgTransformada.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // 4. Usa o método generalizado
        atualizarTelaComImagemTransformada();
    }

    public void RotacionarImagem(BufferedImage imgRotacionar, double anguloEmGraus){
        /*
        Matriz de Rotação Tradicional:
        [ x' ]   [ cos(θ)  -sin(θ) ] [ x ]
        [ y' ] = [ sin(θ)   cos(θ) ] [ y ]
        
        TÉCNICA DE MAPEAMENTO REVERSO:
        Para evitar "buracos" na imagem devido a arredondamentos, varremos a
        imagem de DESTINO (x', y') e usamos a matriz inversa (ângulo negativo) 
        para descobrir de qual (x, y) da origem puxaremos a cor:
        x = x' * cos(θ) + y' * sin(θ)
        y = -x' * sin(θ) + y' * cos(θ)
        */
        
        int largura = imgRotacionar.getWidth();
        int altura = imgRotacionar.getHeight();
        
        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgRotacionar);

        // O Java usa radianos para cálculos trigonométricos
        double radianos = Math.toRadians(anguloEmGraus);
        double cos = Math.cos(radianos);
        double sin = Math.sin(radianos);

        // Precisamos rotacionar a partir do centro da imagem, e não do canto (0,0)
        int centroX = largura / 2;
        int centroY = altura / 2;

        // 2. Varrendo os pixels da nova imagem (DESTINO)
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                
                // Transladamos a coordenada atual para que o centro seja (0,0)
                int x_centro = x - centroX;
                int y_centro = y - centroY;

                // Aplicamos a matriz de Rotação Inversa
                int x_original = (int) Math.round(x_centro * cos + y_centro * sin);
                int y_original = (int) Math.round(-x_centro * sin + y_centro * cos);

                // Transladamos o centro de volta para as coordenadas normais da tela
                x_original += centroX;
                y_original += centroY;

                // 3. Verifica se a coordenada original calculada existe na imagem base
                if (x_original >= 0 && x_original < largura && y_original >= 0 && y_original < altura) {
                    // Pega a cor do pixel lá da imagem original
                    int corOriginal = imgRotacionar.getRGB(x_original, y_original);
                    imgTransformada.setRGB(x, y, corOriginal);
                } else {
                    // Pinta de branco as bordas (áreas vazias que sobraram após rotacionar)
                    imgTransformada.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // 4. Usa o método generalizado
        atualizarTelaComImagemTransformada();
    }

    public void EspelharImagem(BufferedImage imgEspelhar, boolean espelharHorizontal){
        /*
        Matriz de Espelhamento:
        Para espelhar horizontalmente, usamos:
        [ x' ]   [ -1  0 ] [ x ]
        [ y' ] = [  0  1 ] [ y ]

        Para espelhar verticalmente, usamos:
        [ x' ]   [ 1  0 ] [ x ]
        [ y' ] = [ 0 -1 ] [ y ]
        */

        int largura = imgEspelhar.getWidth();
        int altura = imgEspelhar.getHeight();

        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgEspelhar);

        // 2. Varrendo os pixels da nova imagem (DESTINO)
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {

                // Aplicamos a matriz de espelhamento
                int x_original, y_original;
                if (espelharHorizontal) {
                    x_original = largura - 1 - x;
                    y_original = y;
                } else {
                    x_original = x;
                    y_original = altura - 1 - y;
                }

                // 3. Verifica se a coordenada original calculada existe na imagem base
                if (x_original >= 0 && x_original < largura && y_original >= 0 && y_original < altura) {
                    // Pega a cor do pixel lá da imagem original
                    int corOriginal = imgEspelhar.getRGB(x_original, y_original);
                    imgTransformada.setRGB(x, y, corOriginal);
                } else {
                    // Pinta de branco as bordas (áreas vazias que sobraram após espelhar)
                    imgTransformada.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // 4. Usa o método generalizado
        atualizarTelaComImagemTransformada();
    }

    public void AmpliarReduzirImagem(BufferedImage imgAmpliar, double fator){
        /*
        Matriz de Ampliação/Redução:
        [ x' ]   [ s  0 ] [ x ]
        [ y' ] = [ 0  s ] [ y ]

        Onde s é o fator de ampliação/redução. Para o mapeamento reverso:
        x = x' / s
        y = y' / s
        */

        int largura = imgAmpliar.getWidth();
        int altura = imgAmpliar.getHeight();

        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgAmpliar);

        // 2. Varrendo os pixels da nova imagem (DESTINO)
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {

                // Aplicamos a matriz de ampliação inversa
                int x_original = (int) Math.round(x / fator);
                int y_original = (int) Math.round(y / fator);

                // 3. Verifica se a coordenada original calculada existe na imagem base
                if (x_original >= 0 && x_original < largura && y_original >= 0 && y_original < altura) {
                    // Pega a cor do pixel lá da imagem original
                    int corOriginal = imgAmpliar.getRGB(x_original, y_original);
                    imgTransformada.setRGB(x, y, corOriginal);
                } else {
                    // Pinta de branco as bordas (áreas vazias que sobraram após ampliação/redução)
                    imgTransformada.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // 4. Usa o método generalizado
        atualizarTelaComImagemTransformada();
    }

    public void GrayscaleImagem(BufferedImage imgGrayscale){
        /*
        Para converter uma imagem colorida em grayscale, podemos usar a fórmula de luminosidade:
        Gray = 0.299 * R + 0.587 * G + 0.114 * B
        */

        int largura = imgGrayscale.getWidth();
        int altura = imgGrayscale.getHeight();

        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgGrayscale);

        // 2. Varrendo os pixels da imagem original
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int rgb = imgGrayscale.getRGB(x, y);
                Color cor = new Color(rgb);

                // Calcula o valor de cinza usando a fórmula de luminosidade
                int grayValue = (int) Math.round(0.299 * cor.getRed() + 0.587 * cor.getGreen() + 0.114 * cor.getBlue());
                Color grayColor = new Color(grayValue, grayValue, grayValue);

                imgTransformada.setRGB(x, y, grayColor.getRGB());
            }
        }

        // 3. Usa o método generalizado
        atualizarTelaComImagemTransformada();
    }

    public void BrilhoImagen(BufferedImage imgGrayscale, double brilho) {
        /*
        Para ajustar o brilho de uma imagem, podemos simplesmente adicionar um valor constante a cada componente de cor:
        R' = R + brilho
        G' = G + brilho
        B' = B + brilho

        Onde "brilho" pode ser positivo (para clarear) ou negativo (para escurecer).
        */

        int largura = imgGrayscale.getWidth();
        int altura = imgGrayscale.getHeight();

        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgGrayscale);

        // 2. Varrendo os pixels da imagem original
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int rgb = imgGrayscale.getRGB(x, y);

                Color cor = new Color(rgb);

                // Ajusta o brilho adicionando o valor constante a cada componente
                int red = Math.min(255, Math.max(0, cor.getRed() + (int) brilho));
                int green = Math.min(255, Math.max(0, cor.getGreen() + (int) brilho));
                int blue = Math.min(255, Math.max(0, cor.getBlue() + (int) brilho));

                Color adjustedColor = new Color(red, green, blue);
                imgTransformada.setRGB(x, y, adjustedColor.getRGB());
            }
        }

        // 3. Usa o método generalizado
        atualizarTelaComImagemTransformada();
    }

    public void ContrasteImagen(BufferedImage imgGrayscale, double contraste) {
        /*
        Para ajustar o contraste de uma imagem, podemos usar a seguinte fórmula:
        R' = (R - 128) * contraste + 128
        G' = (G - 128) * contraste + 128
        B' = (B - 128) * contraste + 128

        Onde "contraste" é um fator que pode ser maior que 1 para aumentar o contraste ou entre 0 e 1 para diminuir o contraste.
        */

        int largura = imgGrayscale.getWidth();
        int altura = imgGrayscale.getHeight();

        // 1. Usa o método generalizado
        imgTransformada = criarImagemEmBranco(imgGrayscale);

        // 2. Varrendo os pixels da imagem original
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int rgb = imgGrayscale.getRGB(x, y);
                Color cor = new Color(rgb);

                // Ajusta o contraste usando a fórmula
                int red = (int) Math.round((cor.getRed() - 128) * contraste + 128);
                int green = (int) Math.round((cor.getGreen() - 128) * contraste + 128);
                int blue = (int) Math.round((cor.getBlue() - 128) * contraste + 128);

                // Garante que os valores estejam no intervalo [0, 255]
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                Color adjustedColor = new Color(red, green, blue);
                imgTransformada.setRGB(x, y, adjustedColor.getRGB());
            }
        }

        // 3. Usa o método generalizado
        atualizarTelaComImagemTransformada();

    }

    public static void main(String[] args) {
        // Inicia a aplicação na Thread de Eventos do Swing
        SwingUtilities.invokeLater(() -> {
            ProcessamentoImagens app = new ProcessamentoImagens();
            app.setVisible(true);
        });
    }
}