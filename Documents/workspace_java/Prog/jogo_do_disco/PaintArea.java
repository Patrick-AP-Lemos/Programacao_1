import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PaintArea extends JPanel {
	// Constantes do jogo
	private static final int BALL_SIZE = 60;
	private static final int PLAYER_WIDTH = 100, PLAYER_HEIGHT = 180;
	private static final int SPEED = 5, DIFERENCIAL = 30;
	private static final int SCORE_AREA_HEIGHT = 76;
	private static final int GOAL_WIDTH = 10, GOAL_HEIGHT = 300;
	private static final int PLAYER_MARGIN = 60;
	private static final int COLISAO_LIMIT = 20;

	// Estados do jogo
	private int ballX = 400, ballY = 400;
	private int ballSpeedX = 1, ballSpeedY = 1;
	private int player1Y = 360, player2Y = 360;
	private int score1 = 0, score2 = 0;
	private int speed_increment = 0, colisao = 0;
	private boolean paused = false;

	// Imagens
	private Image imgPlayer1, imgPlayer2, imgBackground;

	// Temporizadores
	private Timer timer, pause;

	public PaintArea() {
		setFocusable(true);
	    carregarImagens();
	    addKeyListener(new PlayerControl());
	    iniciarJogo();
	}

	private void carregarImagens() {
		imgPlayer1 = new ImageIcon("jogador1_W.png").getImage();
		imgPlayer2 = new ImageIcon("jogador2_UP.png").getImage();
		imgBackground = new ImageIcon("mesa.png").getImage();
	}

	private class PlayerControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int velocidade = SPEED + speed_increment + DIFERENCIAL;

			switch (e.getKeyCode()) {
				case KeyEvent.VK_W -> moverPlayer1(-velocidade, "jogador1_W.png");
				case KeyEvent.VK_S -> moverPlayer1(velocidade, "jogador1_S.png");
				case KeyEvent.VK_UP -> moverPlayer2(-velocidade, "jogador2_UP.png");
				case KeyEvent.VK_DOWN -> moverPlayer2(velocidade, "jogador2_DOWN.png");
			}
		}

		private void moverPlayer1(int dy, String img) {
			player1Y = Math.max(SCORE_AREA_HEIGHT, Math.min(getHeight() - PLAYER_HEIGHT, player1Y + dy));
			imgPlayer1 = new ImageIcon(img).getImage();
		}

		private void moverPlayer2(int dy, String img) {
			player2Y = Math.max(SCORE_AREA_HEIGHT, Math.min(getHeight() - PLAYER_HEIGHT, player2Y + dy));
			imgPlayer2 = new ImageIcon(img).getImage();
		}
	}

	private void iniciarJogo() {
		timer = new Timer((SPEED + speed_increment), e -> atualizarEstadoJogo());
		timer.start();
	}

    private void atualizarEstadoJogo() {
        int goalY = (getHeight() / 2 - GOAL_HEIGHT / 2) + SCORE_AREA_HEIGHT - 40;

        if (!paused) {
            ballX += (SPEED + speed_increment) * ballSpeedX;
            ballY += (SPEED + speed_increment) * ballSpeedY;
        }

        Rectangle ball = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
        Rectangle player1 = new Rectangle(PLAYER_MARGIN, player1Y, PLAYER_WIDTH, PLAYER_HEIGHT);
        Rectangle player2 = new Rectangle(getWidth() - PLAYER_MARGIN - PLAYER_WIDTH, player2Y, PLAYER_WIDTH, PLAYER_HEIGHT);

        // Colisões
        if (ballX <= 0 || ballX + BALL_SIZE >= getWidth()) {
            ballSpeedX *= -1;
            colisao++;
        }
        if (ballY < SCORE_AREA_HEIGHT || ballY + BALL_SIZE >= getHeight()) {
            ballY = Math.max(ballY, SCORE_AREA_HEIGHT);
            ballSpeedY *= -1;
            colisao++;
        }
        if (ballSpeedX < 0 && ball.intersects(player1)) {
            ballSpeedX = 1;
            colisao++;
        }
        if (ballSpeedX > 0 && ball.intersects(player2)) {
            ballSpeedX = -1;
            colisao++;
        }

        // Gols
        Rectangle leftGoal = new Rectangle(0, goalY, GOAL_WIDTH, (GOAL_HEIGHT - 10));
        Rectangle rightGoal = new Rectangle(getWidth() - GOAL_WIDTH, goalY, GOAL_WIDTH, GOAL_HEIGHT);

        if (ball.intersects(leftGoal)) {
            score2++;
            resetBall(true);
        } else if (ball.intersects(rightGoal)) {
            score1++;
            resetBall(false);
        }

        if (colisao >= COLISAO_LIMIT) {
            colisao = 0;
            if(speed_increment <= 6) speed_increment++;
        }

        repaint();
    }

    private void resetBall(boolean leftScored) {
        ballX = (getWidth() / 2) - (BALL_SIZE / 2);
        ballY = getHeight() / 2;
        ballSpeedX = leftScored ? 1 : -1;
        ballSpeedY = Math.random() > 0.5 ? 1 : -1;

        paused = true;
        pause = new Timer(500, e -> {
            paused = false;
            ((Timer) e.getSource()).stop();
        });
        pause.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int goalY = (getHeight() / 2 - GOAL_HEIGHT / 2) + SCORE_AREA_HEIGHT - 40;

        g.drawImage(imgBackground, 0, 0, getWidth(), getHeight(), this);

        // Gols
        g.setColor(Color.CYAN);
        g.fillRect(0, goalY, GOAL_WIDTH, GOAL_HEIGHT);
        g.fillRect(getWidth() - GOAL_WIDTH, goalY, GOAL_WIDTH, GOAL_HEIGHT);

        // Bola
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Jogadores
        g.drawImage(imgPlayer1, PLAYER_MARGIN, player1Y, PLAYER_WIDTH, PLAYER_HEIGHT, this);
        g.drawImage(imgPlayer2, getWidth() - PLAYER_MARGIN - PLAYER_WIDTH, player2Y, PLAYER_WIDTH, PLAYER_HEIGHT, this);

        // Pontuação
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Player 1: " + score1, 30, 60);
        g.drawString("Player 2: " + score2, getWidth() - 200, 60);
        g.drawString("Nível: " + (speed_increment + 1), getWidth() / 2 - 55, 40);

        Toolkit.getDefaultToolkit().sync();
    }
}
