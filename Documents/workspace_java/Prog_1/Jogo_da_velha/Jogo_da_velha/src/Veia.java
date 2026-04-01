import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Veia extends JFrame: A classe Veia herda de JFrame
 * Veia implements ActionListener: A classe Veia implementa a interface ActionListener
 */
public class Veia extends JFrame implements ActionListener {

	private static Component NULL;
	private final int VAZIO = 0;
	private final int XIS = 1;
	private final int BOLA = 2;
	private int cont = 0;
	private int verifica = 0;

	int quemJogaAgora = XIS; 
	
	private ImageIcon bola = createImageIcon("imagens/bola.png", "bola" );
	private ImageIcon xis = createImageIcon("imagens/xis.png", "xis" );

	JButton botao [][] = new JButton [3][3]; 
	int matrizConteudo [][] = new int [3][3]; 
	JButton novoJogo = new JButton();
	JButton sugestao = new JButton();
	
	private Container container;
	
	Veia estaClasse;
	
	public Veia() {
		
		super( "Universidade Feevale - Jogo da Velha" );
		
		Image icone = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource( "imagens/veia.png" )); 
		setIconImage(icone); 
		
		estaClasse = this;
		
		container = getContentPane();
		container.setLayout( null );

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				botao[i][j] = new JButton();
				botao[i][j].setBounds(40 + j * 110, 40 + i * 110, 80, 80);
				botao[i][j].setActionCommand("botao" + i + j);
				botao[i][j].addActionListener(this);
				container.add(botao[i][j]);
			}
		}
		
		novoJogo = new JButton( "Novo Jogo" );
		novoJogo.setBounds( 40, 370, 120, 40 );
		novoJogo.setActionCommand( "novoJogo" );
		novoJogo.addActionListener( this );

		sugestao = new JButton( "Auto" );
		sugestao.setBounds( 220, 370, 120, 40 );
		sugestao.setActionCommand( "Auto" );
		sugestao.addActionListener( this );

		container.add( botao[0][0] );
		container.add( botao[0][1] );
		container.add( botao[0][2] );
		container.add( botao[1][0] );
		container.add( botao[1][1] );
		container.add( botao[1][2] );
		container.add( botao[2][0] );
		container.add( botao[2][1] );
		container.add( botao[2][2] );
		container.add( novoJogo );
		container.add( sugestao );
		
		novoJogo();
		
		setSize( 400, 500 );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("novoJogo")) {
			novoJogo();
		} else if (cmd.equals("Auto")) {
			sugestao();
		} else if (cmd.startsWith("botao")) {
			int i = Character.getNumericValue(cmd.charAt(5));
			int j = Character.getNumericValue(cmd.charAt(6));
			handleBotaoClick(i, j);
		}
	}

	private void handleBotaoClick(int i, int j) {
		if (matrizConteudo[i][j] != VAZIO) {
			JOptionPane.showMessageDialog(this, "Botão já está marcado!");
			return;
		}
		if (quemJogaAgora == XIS) {
			matrizConteudo[i][j] = XIS;
			botao[i][j].setIcon(xis);
			quemJogaAgora = BOLA;
		} else {
			matrizConteudo[i][j] = BOLA;
			botao[i][j].setIcon(bola);
			quemJogaAgora = XIS;
		}
		analisaSeAlguemVenceuOuSeDeuVelha();
	}
	
	private static final int[][] COMBINACOES = {
		{0,0, 0,1, 0,2}, // linha 0
		{1,0, 1,1, 1,2}, // linha 1
		{2,0, 2,1, 2,2}, // linha 2
		{0,0, 1,0, 2,0}, // coluna 0
		{0,1, 1,1, 2,1}, // coluna 1
		{0,2, 1,2, 2,2}, // coluna 2
		{0,0, 1,1, 2,2}, // diagonal primária
		{0,2, 1,1, 2,0}  // diagonal secundária
	};

	private int verificaVencedor() {
		for (int[] c : COMBINACOES) {
			int a = matrizConteudo[c[0]][c[1]];
			int b = matrizConteudo[c[2]][c[3]];
			int d = matrizConteudo[c[4]][c[5]];
			if (a != VAZIO && a == b && b == d) return a;
		}
		return VAZIO;
	}

	/**
	 * Esta função é a que é o trabalho (0-9,5 pontos)
	 */
	private void analisaSeAlguemVenceuOuSeDeuVelha() {
		cont++;
		int vencedor = verificaVencedor();
		if (vencedor != VAZIO) {
			String nome = (vencedor == XIS) ? "X" : "O";
			JOptionPane.showMessageDialog(this, "Jogador " + nome + "\nGANHOUU!");
			novoJogo();
		} else if (cont == 9) {
			JOptionPane.showMessageDialog(this, "DEU VELHA!");
			novoJogo();
		}
	}
	
	/**
	 * Esta função é a que é o plus a mais do trabalho (0-0,5 pontos)
	 */
	private void sugestao() {
		verifica = 0;
		if(cont < 3) {
			if(quemJogaAgora == XIS) {
				marcaRANDOM(XIS);
			}else if(quemJogaAgora == BOLA){
				marcaRANDOM(BOLA);
			}
		}else if(cont == 3) {
			if(quemJogaAgora == XIS) {
				for(int j = 0; j < 3; j++) {
					for(int i = 0; i < 3; i++) {
						if(matrizConteudo[i][j] == VAZIO) {
							if(i == 0) {
								if(matrizConteudo[i + 1][j] == BOLA && matrizConteudo[i + 2][j] == BOLA) marcaXIS(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaXIS(i, j);
									else if(matrizConteudo[i + 1][j + 1] == BOLA && matrizConteudo[i + 2][j + 2] == BOLA)marcaXIS(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaXIS(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaXIS(i, j);
									else if(matrizConteudo[i + 1][j - 1] == BOLA && matrizConteudo[i + 2][j - 2] == BOLA)marcaXIS(i, j);
								}
							}else if(i == 1) {
								if(matrizConteudo[i - 1][j] == BOLA && matrizConteudo[i + 1][j] == BOLA) marcaXIS(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaXIS(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j - 1] == BOLA && matrizConteudo[i + 1][j + 1] == BOLA)marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j + 1] == BOLA && matrizConteudo[i + 1][j - 1] == BOLA)marcaXIS(i, j);
								}else if(j == 2) if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaXIS(i, j);
							}else if(i == 2) {
								if(matrizConteudo[i - 1][j] == BOLA && matrizConteudo[i - 2][j] == BOLA) marcaXIS(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j + 1] == BOLA && matrizConteudo[i - 2][j + 2] == BOLA)marcaXIS(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaXIS(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j - 1] == BOLA && matrizConteudo[i - 2][j - 2] == BOLA)marcaXIS(i, j);
								}
							}
						}
					}
				}
				if(verifica == 0) marcaRANDOM(XIS);
			}else if(quemJogaAgora == BOLA) {
				for(int j = 0; j < 3; j++) {
					for(int i = 0; i < 3; i++) {
						if(matrizConteudo[i][j] == VAZIO) {
							if(i == 0) {
								if(matrizConteudo[i + 1][j] == XIS && matrizConteudo[i + 2][j] == XIS) marcaBOLA(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaBOLA(i, j);
									else if(matrizConteudo[i + 1][j + 1] == XIS && matrizConteudo[i + 2][j + 2] == XIS)marcaBOLA(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaBOLA(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaBOLA(i, j);
									else if(matrizConteudo[i + 1][j - 1] == XIS && matrizConteudo[i + 2][j - 2] == XIS)marcaBOLA(i, j);
								}
							}else if(i == 1) {
								if(matrizConteudo[i - 1][j] == XIS && matrizConteudo[i + 1][j] == XIS) marcaBOLA(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaBOLA(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j - 1] == XIS && matrizConteudo[i + 1][j + 1] == XIS)marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j + 1] == XIS && matrizConteudo[i + 1][j - 1] == XIS)marcaBOLA(i, j);
								}else if(j == 2) if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaBOLA(i, j);
							}else if(i == 2) {
								if(matrizConteudo[i - 1][j] == XIS && matrizConteudo[i - 2][j] == XIS) marcaBOLA(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j + 1] == XIS && matrizConteudo[i - 2][j + 2] == XIS)marcaBOLA(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaBOLA(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j - 1] == XIS && matrizConteudo[i - 2][j - 2] == XIS)marcaBOLA(i, j);
								}
							}
						}
					}
				}
				if(verifica == 0) marcaRANDOM(BOLA);
			}
		}else if(cont > 3){
			if(quemJogaAgora == XIS) {
				for(int j = 0; j < 3; j++) {
					for(int i = 0; i < 3; i++) {
						if(matrizConteudo[i][j] == VAZIO) {
							if(i == 0) {
								if(matrizConteudo[i + 1][j] == XIS && matrizConteudo[i + 2][j] == XIS) marcaXIS(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaXIS(i, j);
									else if(matrizConteudo[i + 1][j + 1] == XIS && matrizConteudo[i + 2][j + 2] == XIS)marcaXIS(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaXIS(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaXIS(i, j);
									else if(matrizConteudo[i + 1][j - 1] == XIS && matrizConteudo[i + 2][j - 2] == XIS)marcaXIS(i, j);
								}
							}else if(i == 1) {
								if(matrizConteudo[i - 1][j] == XIS && matrizConteudo[i + 1][j] == XIS) marcaXIS(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaXIS(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j - 1] == XIS && matrizConteudo[i + 1][j + 1] == XIS)marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j + 1] == XIS && matrizConteudo[i + 1][j - 1] == XIS)marcaXIS(i, j);
								}else if(j == 2) if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaXIS(i, j);
							}else if(i == 2) {
								if(matrizConteudo[i - 1][j] == XIS && matrizConteudo[i - 2][j] == XIS) marcaXIS(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j + 1] == XIS && matrizConteudo[i - 2][j + 2] == XIS)marcaXIS(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaXIS(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaXIS(i, j);
									else if(matrizConteudo[i - 1][j - 1] == XIS && matrizConteudo[i - 2][j - 2] == XIS)marcaXIS(i, j);
								}
							}
						}
					}
				}
				if(verifica == 0) {
					for(int j = 0; j < 3; j++) {
						for(int i = 0; i < 3; i++) {
							if(matrizConteudo[i][j] == VAZIO) {
								if(i == 0) {
									if(matrizConteudo[i + 1][j] == BOLA && matrizConteudo[i + 2][j] == BOLA) marcaXIS(i, j);
									else if(j == 0) {
										if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaXIS(i, j);
										else if(matrizConteudo[i + 1][j + 1] == BOLA && matrizConteudo[i + 2][j + 2] == BOLA)marcaXIS(i, j);
									}else if(j == 1) {
										if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaXIS(i, j);
									}else if(j == 2) {
										if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaXIS(i, j);
										else if(matrizConteudo[i + 1][j - 1] == BOLA && matrizConteudo[i + 2][j - 2] == BOLA)marcaXIS(i, j);
									}
								}else if(i == 1) {
									if(matrizConteudo[i - 1][j] == BOLA && matrizConteudo[i + 1][j] == BOLA) marcaXIS(i, j);
									else if(j == 0) {
										if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaXIS(i, j);
									}else if(j == 1) {
										if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaXIS(i, j);
										else if(matrizConteudo[i - 1][j - 1] == BOLA && matrizConteudo[i + 1][j + 1] == BOLA)marcaXIS(i, j);
										else if(matrizConteudo[i - 1][j + 1] == BOLA && matrizConteudo[i + 1][j - 1] == BOLA)marcaXIS(i, j);
									}else if(j == 2) if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaXIS(i, j);
								}else if(i == 2) {
									if(matrizConteudo[i - 1][j] == BOLA && matrizConteudo[i - 2][j] == BOLA) marcaXIS(i, j);
									else if(j == 0) {
										if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaXIS(i, j);
										else if(matrizConteudo[i - 1][j + 1] == BOLA && matrizConteudo[i - 2][j + 2] == BOLA)marcaXIS(i, j);
									}else if(j == 1) {
										if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaXIS(i, j);
									}else if(j == 2) {
										if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaXIS(i, j);
										else if(matrizConteudo[i - 1][j - 1] == BOLA && matrizConteudo[i - 2][j - 2] == BOLA)marcaXIS(i, j);
									}
								}
							}
						}
					}
				}
				if(verifica == 0) marcaRANDOM(XIS);
			}else if(quemJogaAgora == BOLA) {
				for(int j = 0; j < 3; j++) {
					for(int i = 0; i < 3; i++) {
						if(matrizConteudo[i][j] == VAZIO) {
							if(i == 0) {
								if(matrizConteudo[i + 1][j] == BOLA && matrizConteudo[i + 2][j] == BOLA) marcaBOLA(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaBOLA(i, j);
									else if(matrizConteudo[i + 1][j + 1] == BOLA && matrizConteudo[i + 2][j + 2] == BOLA)marcaBOLA(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaBOLA(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaBOLA(i, j);
									else if(matrizConteudo[i + 1][j - 1] == BOLA && matrizConteudo[i + 2][j - 2] == BOLA)marcaBOLA(i, j);
								}
							}else if(i == 1) {
								if(matrizConteudo[i - 1][j] == BOLA && matrizConteudo[i + 1][j] == BOLA) marcaBOLA(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaBOLA(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j - 1] == BOLA && matrizConteudo[i + 1][j + 1] == BOLA)marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j + 1] == BOLA && matrizConteudo[i + 1][j - 1] == BOLA)marcaBOLA(i, j);
								}else if(j == 2) if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaBOLA(i, j);
							}else if(i == 2) {
								if(matrizConteudo[i - 1][j] == BOLA && matrizConteudo[i - 2][j] == BOLA) marcaBOLA(i, j);
								else if(j == 0) {
									if(matrizConteudo[i][j + 1] == BOLA && matrizConteudo[i][j + 2] == BOLA) marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j + 1] == BOLA && matrizConteudo[i - 2][j + 2] == BOLA)marcaBOLA(i, j);
								}else if(j == 1) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j + 1] == BOLA) marcaBOLA(i, j);
								}else if(j == 2) {
									if(matrizConteudo[i][j - 1] == BOLA && matrizConteudo[i][j - 2] == BOLA) marcaBOLA(i, j);
									else if(matrizConteudo[i - 1][j - 1] == BOLA && matrizConteudo[i - 2][j - 2] == BOLA)marcaBOLA(i, j);
								}
							}
						}
					}
				}
				if(verifica == 0) {
					for(int j = 0; j < 3; j++) {
						for(int i = 0; i < 3; i++) {
							if(matrizConteudo[i][j] == VAZIO) {
								if(i == 0) {
									if(matrizConteudo[i + 1][j] == XIS && matrizConteudo[i + 2][j] == XIS) marcaBOLA(i, j);
									else if(j == 0) {
										if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS)marcaBOLA(i, j);
										else if(matrizConteudo[i + 1][j + 1] == XIS && matrizConteudo[i + 2][j + 2] == XIS)marcaBOLA(i, j);
									}else if(j == 1) {
										if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaBOLA(i, j);
									}else if(j == 2) {
										if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaBOLA(i, j);
										else if(matrizConteudo[i + 1][j - 1] == XIS && matrizConteudo[i + 2][j - 2] == XIS)marcaBOLA(i, j);
									}
								}else if(i == 1) {
									if(matrizConteudo[i - 1][j] == XIS && matrizConteudo[i + 1][j] == XIS) marcaBOLA(i, j);
									else if(j == 0) {
										if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaBOLA(i, j);
									}else if(j == 1) {
										if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaBOLA(i, j);
										else if(matrizConteudo[i - 1][j - 1] == XIS && matrizConteudo[i + 1][j + 1] == XIS)marcaBOLA(i, j);
										else if(matrizConteudo[i - 1][j + 1] == XIS && matrizConteudo[i + 1][j - 1] == XIS)marcaBOLA(i, j);
									}else if(j == 2) if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaBOLA(i, j);
								}else if(i == 2) {
									if(matrizConteudo[i - 1][j] == XIS && matrizConteudo[i - 2][j] == XIS) marcaBOLA(i, j);
									else if(j == 0) {
										if(matrizConteudo[i][j + 1] == XIS && matrizConteudo[i][j + 2] == XIS) marcaBOLA(i, j);
										else if(matrizConteudo[i - 1][j + 1] == XIS && matrizConteudo[i - 2][j + 2] == XIS)marcaBOLA(i, j);
									}else if(j == 1) {
										if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j + 1] == XIS) marcaBOLA(i, j);
									}else if(j == 2) {
										if(matrizConteudo[i][j - 1] == XIS && matrizConteudo[i][j - 2] == XIS) marcaBOLA(i, j);
										else if(matrizConteudo[i - 1][j - 1] == XIS && matrizConteudo[i - 2][j - 2] == XIS)marcaBOLA(i, j);
									}
								}
							}
						}
					}
				}
				if(verifica == 0) marcaRANDOM(BOLA);
			}
		}
		analisaSeAlguemVenceuOuSeDeuVelha();
	}
	
	private void novoJogo() {
		JOptionPane.showMessageDialog(estaClasse, "Vamos jogar Jogo da velha?" );

		cont = 0;
		
		// Limpo a matriz
		for (int i = 0; i < 3; i++ ) {
			for (int j = 0; j < 3; j++ ) {
				matrizConteudo[i][j] = VAZIO;
			}
		}
		// Limpo os botões
		for ( int i=0; i<3;i++ ) {
			for ( int j=0; j<3;j++ ) {
				botao[i][j].setIcon( null );
			}
		}
	
	}
	
    protected static ImageIcon createImageIcon(String path,
            String description) {
    	java.net.URL imgURL = Veia.class.getResource(path);

    	if (imgURL != null) {
    		return new ImageIcon(imgURL, description);
    	} else {
    		System.err.println("Nao achei o arquivo: " + path);
    		return null;
    	}
    }

	
	public static void main( String args[] ) {
		
		Veia v = new Veia();
        v.setVisible( true );
	}
	
	public void marcaXIS(int i, int j) {
		quemJogaAgora = BOLA;
		if(verifica == 0) {
			matrizConteudo[i][j] = XIS;
			botao[i][j].setIcon( (Icon) xis );
		}
		verifica = 1;
//		JOptionPane.showMessageDialog(estaClasse, "X" );//TESTE
	}
	
	public void marcaBOLA(int i, int j) {
		quemJogaAgora = XIS;
		if(verifica == 0) {
			matrizConteudo[i][j] = BOLA;
			botao[i][j].setIcon( (Icon) bola );
		}
		verifica = 1;
//		JOptionPane.showMessageDialog(estaClasse, "O" );//TESTE
	}
	
	public void marcaRANDOM(int quemjoga) {
		Random num = new Random();
		int i, j, k = 0;
		
		if(quemjoga == XIS) {
			while(k != 1) {
				i = num.nextInt(3);
				j = num.nextInt(3);
				if(matrizConteudo[i][j] == VAZIO) {
					marcaXIS(i, j);
					k = 1;
				}
			}
		}else if(quemjoga == BOLA) {
			while(k != 1) {
				i = num.nextInt(3);
				j = num.nextInt(3);
				if(matrizConteudo[i][j] == VAZIO) {
					marcaBOLA(i, j);
					k = 1;
				}
			}
		}
	}
	
}