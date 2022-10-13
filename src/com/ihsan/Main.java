package com.ihsan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {

	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 500;

	private final int WIDTH = 4;
	private final int HEIGHT = 4;
	private final int COLORS = 8;
	private final int MAX_PER_COLOR = 2;
	private final int BUT_SIZE = 100;

	private int firstSelectedButton = -1;
	private int secondSelectedButton = -1;

	private JFrame guiFrame;

	private ColorButton[] arrayButtons;

	private Random rand = new Random();

	private int turn;

	private int player0Score;
	private int player1Score;

	JLabel lblPlayer0;

	JLabel lblPlayer1;

	public Main() {

		turn = 0;
		arrayButtons = new ColorButton[WIDTH * HEIGHT];
		guiFrame = new JFrame();
		player1Score = 0;
		player0Score = 0;
	}

	public void updatePlayerInfo(JLabel player, int score, String message, int playerId, Color color) {

		String playertxt = "Player " + playerId + ", Score: " + score + " " + message;

		player.setText(playertxt);
		player.setBackground(color);
		player.repaint();
	}

	public void initNewGame() {

		int buttonId;

		player0Score = 0;
		player1Score = 0;

		turn = 0;

		firstSelectedButton = -1;
		secondSelectedButton = -1;

		Color[] colorarr = produceRandomColors();

		ArrayList<Integer> index = new ArrayList<>();

		for (buttonId = 0; buttonId < WIDTH * HEIGHT; buttonId++) {

			index.add(buttonId);

		}

		Color color;
		int k = 0;

		for (int i = 0; i < colorarr.length; i++) {

			for (int j = 0; j < MAX_PER_COLOR; j++) {

				k = rand.nextInt(index.size());

				buttonId = index.get(k);

				color = colorarr[i];

				index.remove(k);

				arrayButtons[buttonId].setDrawColor(color);

			}

		}

		for (ColorButton button : arrayButtons) {
			button.highlightBorder(false);
			button.setRevealed(false);
			button.setMatched(false);
			button.flip(false);
		}

		updatePlayerInfo(lblPlayer0, player0Score, "-Choose your first square", 0, Color.GREEN);

		updatePlayerInfo(lblPlayer1, player1Score, "-Player 0's turn.Please Wait.", 1, Color.RED);

	}

	public void createGUI() {

		int buttonId;

		guiFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		guiFrame.setTitle("MATCHING GAME");

		Color color;
		int k = 0;

		for (int i = 0; i < arrayButtons.length; i++) {

			arrayButtons[i] = new ColorButton(i, this, BUT_SIZE, BUT_SIZE, Color.DARK_GRAY, 6, Color.DARK_GRAY);
			arrayButtons[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ColorButton btn;
					btn = (ColorButton) e.getSource();
					buttonClicked(btn.getButtonID());

				}
			});

			arrayButtons[i].addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					buttonExited(e);

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

					buttonEntered(e);

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});

		}

		JPanel panel;
		panel = new JPanel();

		// panel.setPreferredSize(new Dimension(440, 440));

		panel.setBackground(Color.gray);

		panel.setLayout(new GridLayout(WIDTH, HEIGHT, 2, 2));

		for (int i = 0; i < WIDTH * HEIGHT; i++) {

			panel.add(arrayButtons[i]);

		}

		guiFrame.getContentPane().setLayout(new BorderLayout());
		guiFrame.getContentPane().add(panel, BorderLayout.CENTER);

		lblPlayer0 = new JLabel("Player 0");

		lblPlayer1 = new JLabel("Player 1");

		lblPlayer0.setOpaque(true);
		lblPlayer1.setOpaque(true);

		guiFrame.getContentPane().add(lblPlayer0, BorderLayout.PAGE_START);

		guiFrame.getContentPane().add(lblPlayer1, BorderLayout.PAGE_END);

		// guiFrame.getContentPane().add(button, BorderLayout.PAGE_END);

		initNewGame();

		guiFrame.setVisible(true);

	}

	private Color[] produceRandomColors() {

		Color[] colorarr = new Color[COLORS];

		for (int i = 0; i < colorarr.length; i++) {

			int r = 32 * rand.nextInt(8);
			int g = 32 * rand.nextInt(8);
			int b = 32 * rand.nextInt(8);
			colorarr[i] = new Color(r, g, b);

		}

		return colorarr;
	}

	public void buttonEntered(MouseEvent e) {

		ColorButton button = (ColorButton) (e.getSource());
		button.highlightBorder(true);

	}

	public void buttonExited(MouseEvent e) {

		ColorButton button = (ColorButton) (e.getSource());
		button.highlightBorder(false);

	}

	public void buttonClicked(int buttonID) {

		ColorButton firstButton;
		ColorButton secondButton;
		ColorButton clickedButton = arrayButtons[buttonID];

		if (clickedButton.isMatched()) { // if button is set to black. Prevents selecting.

			return;
		}

		if (firstSelectedButton < 0) {

			firstSelectedButton = buttonID;
			firstButton = arrayButtons[firstSelectedButton];
			firstButton.highlightBorder(true);
			firstButton.flip(true);

			if (turn == 0) { // player 0's turn.

				updatePlayerInfo(lblPlayer0, player0Score, "-Choose your second square", 0, Color.GREEN);

				updatePlayerInfo(lblPlayer1, player1Score, "-Player 0's turn.Please Wait.", 1, Color.RED);

			} else { // player 1's turn.

				updatePlayerInfo(lblPlayer0, player0Score, "-Player 1's turn.Please Wait.", 0, Color.RED);

				updatePlayerInfo(lblPlayer1, player1Score, "-Choose your second square", 1, Color.GREEN);

			}

		}

		else if (firstSelectedButton >= 0 && secondSelectedButton < 0 && firstSelectedButton != buttonID) {

			firstButton = arrayButtons[firstSelectedButton];
			firstButton.highlightBorder(false);

			secondSelectedButton = buttonID;
			secondButton = arrayButtons[secondSelectedButton];
			secondButton.highlightBorder(true);
			secondButton.flip(true);

			if (firstButton.getDrawColor().equals(secondButton.getDrawColor())) { // matched

				if (turn == 0) { // player 0's turn.

					updatePlayerInfo(lblPlayer0, player0Score, "-Well Done - click a square to continue", 0,
							Color.GREEN);

					updatePlayerInfo(lblPlayer1, player1Score, "-Player 0's turn.Please Wait.", 1, Color.RED);

				} else { // player 1's turn.

					updatePlayerInfo(lblPlayer0, player0Score, "-Player 1's turn.Please Wait.", 0, Color.RED);

					updatePlayerInfo(lblPlayer1, player1Score, "-Well Done - click a square to continue", 1,
							Color.GREEN);

				}

			} else {

				if (turn == 0) { // player 0's turn.

					updatePlayerInfo(lblPlayer0, player0Score, "-failed :( - click a square to end turn", 0,
							Color.GREEN);

					updatePlayerInfo(lblPlayer1, player1Score, "-Player 0's turn.Please Wait.", 1, Color.RED);

				} else { // player 1's turn.

					updatePlayerInfo(lblPlayer0, player0Score, "-Player 1's turn.Please Wait.", 0, Color.RED);

					updatePlayerInfo(lblPlayer1, player1Score, "-failed :( - click a square to end turn", 1,
							Color.GREEN);

				}

			}

		}

		else if (firstSelectedButton >= 0 && secondSelectedButton >= 0 && firstSelectedButton != secondSelectedButton) {

			firstButton = arrayButtons[firstSelectedButton];
			secondButton = arrayButtons[secondSelectedButton];

			if (firstButton.getDrawColor().equals(secondButton.getDrawColor())) { // matched

				firstButton.setMatched(true);
				secondButton.setMatched(true);

				if (turn == 0) {

					player0Score += 2;

				} else {

					player1Score += 2;
				}

			} else {

				turn = turn == 1 ? 0 : 1;
			}

			firstButton.highlightBorder(false);
			secondButton.highlightBorder(false);
			firstButton.flip(false);
			secondButton.flip(false);

			firstSelectedButton = -1;
			secondSelectedButton = -1;

			if (turn == 0) { // player 0's turn.

				updatePlayerInfo(lblPlayer0, player0Score, "-Choose your first square", 0, Color.GREEN);

				updatePlayerInfo(lblPlayer1, player1Score, "-Player 0's turn.Please Wait.", 1, Color.RED);

			} else { // player 1's turn.

				updatePlayerInfo(lblPlayer0, player0Score, "-Player 1's turn.Please Wait.", 0, Color.RED);

				updatePlayerInfo(lblPlayer1, player1Score, "-Choose your first square", 1, Color.GREEN);

			}

			if (isAllMatched()) {

				String winnerMessage;

				if (player0Score > player1Score) {

					winnerMessage = "Player 0 wins! Congratulations!";
				}

				else if (player0Score < player1Score) {

					winnerMessage = "Player 1 wins! Congratulations!";
				}

				else {

					winnerMessage = "DRAW!";
				}

				JOptionPane.showMessageDialog(guiFrame, winnerMessage);

				initNewGame();
			}
		}

	}

	public boolean isAllMatched() {

		for (ColorButton button : arrayButtons) {

			if (!button.isMatched()) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Main m = new Main();
		m.createGUI();

	}

}
