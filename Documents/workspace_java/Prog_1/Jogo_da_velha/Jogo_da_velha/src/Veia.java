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

/**
 * Programa do Jogo da Velha
 * Valendo pela 4ª Nota da disciplina de Programacao I
 * Equipe:
 * 1)
 * 2) 
 */
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
		
		botao[0][0] = new JButton();
		botao[0][0].setBounds( 40, 40, 80, 80 );
		botao[0][0].setActionCommand( "botao00" );
		botao[0][0].addActionListener( this );
		
		botao[0][1] = new JButton();
		botao[0][1].setBounds( 150, 40, 80, 80 );
		botao[0][1].setActionCommand( "botao01" );
		botao[0][1].addActionListener( this );

		botao[0][2] = new JButton();
		botao[0][2].setBounds( 260, 40, 80, 80 );
		botao[0][2].setActionCommand( "botao02" );
		botao[0][2].addActionListener( this );
		
		botao[1][0] = new JButton();
		botao[1][0].setBounds( 40, 150, 80, 80 );
		botao[1][0].setActionCommand( "botao10" );
		botao[1][0].addActionListener( this );
		
		botao[1][1] = new JButton();
		botao[1][1].setBounds( 150, 150, 80, 80 );
		botao[1][1].setActionCommand( "botao11" );
		botao[1][1].addActionListener( this );

		botao[1][2] = new JButton();
		botao[1][2].setBounds( 260, 150, 80, 80 );
		botao[1][2].setActionCommand( "botao12" );
		botao[1][2].addActionListener( this );
		
		botao[2][0] = new JButton();
		botao[2][0].setBounds( 40, 260, 80, 80 );
		botao[2][0].setActionCommand( "botao20" );
		botao[2][0].addActionListener( this );
		
		botao[2][1] = new JButton();
		botao[2][1].setBounds( 150, 260, 80, 80 );
		botao[2][1].setActionCommand( "botao21" );
		botao[2][1].addActionListener( this );

		botao[2][2] = new JButton();
		botao[2][2].setBounds( 260, 260, 80, 80 );
		botao[2][2].setActionCommand( "botao22" );
		botao[2][2].addActionListener( this );
		
		novoJogo = new JButton( "Novo Jogo" );
		novoJogo.setBounds( 40, 370, 120, 40 );
		novoJogo.setActionCommand( "novoJogo" );
		novoJogo.addActionListener( this );

		sugestao = new JButton( "Sugestao" );
		sugestao.setBounds( 220, 370, 120, 40 );
		sugestao.setActionCommand( "sugestao" );
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

	public void actionPerformed(ActionEvent arg0) {
		
		if( arg0.getActionCommand().equals( "novoJogo" ) ) {
			novoJogo();

		} else if ( arg0.getActionCommand().equals( "botao00" ) ) {
			
			if ( matrizConteudo[0][0] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[0][0] = XIS;
					botao[0][0].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[0][0] = BOLA;
					botao[0][0].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao01" ) ) {
			
			if ( matrizConteudo[0][1] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[0][1] = XIS;
					botao[0][1].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[0][1] = BOLA;
					botao[0][1].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao02" ) ) {
			
			if ( matrizConteudo[0][2] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[0][2] = XIS;
					botao[0][2].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[0][2] = BOLA;
					botao[0][2].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao10" ) ) {
			
			if ( matrizConteudo[1][0] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[1][0] = XIS;
					botao[1][0].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[1][0] = BOLA;
					botao[1][0].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao11" ) ) {
			
			if ( matrizConteudo[1][1] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[1][1] = XIS;
					botao[1][1].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[1][1] = BOLA;
					botao[1][1].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao12" ) ) {
			
			if ( matrizConteudo[1][2] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[1][2] = XIS;
					botao[1][2].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[1][2] = BOLA;
					botao[1][2].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao20" ) ) {
			
			if ( matrizConteudo[2][0] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[2][0] = XIS;
					botao[2][0].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[2][0] = BOLA;
					botao[2][0].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao21" ) ) {
			
			if ( matrizConteudo[2][1] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[2][1] = XIS;
					botao[2][1].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[2][1] = BOLA;
					botao[2][1].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "botao22" ) ) {
			
			if ( matrizConteudo[2][2] != 0 ) {
				JOptionPane.showMessageDialog(estaClasse, "Botão já está marcado!" );
			} else {
				if ( quemJogaAgora == XIS ) {
					quemJogaAgora = BOLA;
					matrizConteudo[2][2] = XIS;
					botao[2][2].setIcon( (Icon) xis );
				} else {
					quemJogaAgora = XIS;
					matrizConteudo[2][2] = BOLA;
					botao[2][2].setIcon( (Icon) bola );
				}
				analisaSeAlguemVenceuOuSeDeuVelha();
			}
			
		} else if ( arg0.getActionCommand().equals( "sugestao" ) ){
			sugestao();
		}
	}
	
	/**
	 * Esta função é a que é o trabalho (0-9,5 pontos)
	 */
	private void analisaSeAlguemVenceuOuSeDeuVelha() {
		cont ++;
		
		for(int i=0, j=0; i<3; i++, j++) {//=============== JOGADOR X ===============
			if(matrizConteudo[i][0] == XIS && matrizConteudo[i][1] == XIS && matrizConteudo[i][2] == XIS) {//Horizontal
				JOptionPane.showMessageDialog(estaClasse, "Jogador X \nGANHOOUU" );
				novoJogo();
			}
			if(matrizConteudo[0][j] == XIS && matrizConteudo[1][j] == XIS && matrizConteudo[2][j] == XIS) {//Vertical
				JOptionPane.showMessageDialog(estaClasse, "Jogador X \nGANHOOUU" );
				novoJogo();
			}
			if(matrizConteudo[0][0] == XIS && matrizConteudo[1][1] == XIS && matrizConteudo[2][2] == XIS) {//Diagonal Primária
				JOptionPane.showMessageDialog(estaClasse, "Jogador X \nGANHOOUU" );
				novoJogo();
			}
			if(matrizConteudo[0][2] == XIS && matrizConteudo[1][1] == XIS && matrizConteudo[2][0] == XIS) {//Diagonal Secundária
				JOptionPane.showMessageDialog(estaClasse, "Jogador X \nGANHOOUU" );
				novoJogo();
			}
		}
		
		for(int i=0, j=0; i<3; i++, j++) {//=============== JOGADOR O ===============
			if(matrizConteudo[i][0] == BOLA && matrizConteudo[i][1] == BOLA && matrizConteudo[i][2] == BOLA) {//Horizontal
				JOptionPane.showMessageDialog(estaClasse, "Jogador O \nGANHOOUU" );
				novoJogo();
			}
			if(matrizConteudo[0][j] == BOLA && matrizConteudo[1][j] == BOLA && matrizConteudo[2][j] == BOLA) {//Vertical
				JOptionPane.showMessageDialog(estaClasse, "Jogador O \nGANHOOUU" );
				novoJogo();
			}
			if(matrizConteudo[0][0] == BOLA && matrizConteudo[1][1] == BOLA && matrizConteudo[2][2] == BOLA) {//Diagonal Primária
				JOptionPane.showMessageDialog(estaClasse, "Jogador O \nGANHOOUU" );
				novoJogo();
			}
			if(matrizConteudo[0][2] == BOLA && matrizConteudo[1][1] == BOLA && matrizConteudo[2][0] == BOLA) {//Diagonal Secundária
				JOptionPane.showMessageDialog(estaClasse, "Jogador O \nGANHOOUU" );
				novoJogo();
			}
		}
		
		if(cont == 9) {//=============== DEU VELHA ===============
			JOptionPane.showMessageDialog(estaClasse, "DEU VELHA" );
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


