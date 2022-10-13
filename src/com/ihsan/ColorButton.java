package com.ihsan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ColorButton extends JButton {

	private Color drawColor;
	private Color borderColor;
	private Color currentDrawColor;
	private Color currentBorderColor;
	
	private int borderSize;
	private int buttonID;
	private Main main;
	private boolean matched; // show black(use border color)
	private boolean revealed; // show original color(draw color)
	
	
	

	public ColorButton(int buttonID, Main main, int width, int height, Color color, int borderWidth,
			Color borderCol) {
		borderSize = borderWidth;
		drawColor = color;
		borderColor = borderCol;
		currentDrawColor = borderColor;
		currentBorderColor = borderColor;
		//this.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		this.buttonID = buttonID;
		this.main = main;

	}

	public ColorButton(int buttonID, Main main, int width, int height, Color color) {
		// Call the other constructor
		
		this(buttonID, main, width, height, color, 0, Color.black);
		
		
	}

	public boolean isMatched() {

		return matched;
	}

	public boolean isRevealed() {

		return revealed;
	}
	
	public void setRevealed(boolean revealed) {
		
		this.revealed = revealed;
		flip(revealed);
	}
	
	

	public void setMatched(boolean matched) {

		this.matched = matched;
		if(matched) {
			currentDrawColor = Color.black;
			currentBorderColor = Color.black;
			
		} else {
			
			currentDrawColor = drawColor;
			currentBorderColor = borderColor;
		}

	}

	public void flip(boolean revealed) {

		this.revealed = revealed;
		if(!isMatched()) {
			
			if (isRevealed()) {
				currentDrawColor = drawColor;
				currentBorderColor = borderColor;
			} else {
				
				currentDrawColor = currentBorderColor;
				
			} 
			
		}
		
		repaint();

	}

	public int getButtonID() {

		return buttonID;

	}

	public Main getMain() {

		return main;
	}

	public void setDrawColor(Color color) {
		drawColor = color;

	}
	
	public Color getDrawColor() {
		
		return drawColor;
	}
	
	public void highlightBorder(boolean highlight) {
		
		if(highlight) {
			setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		} else {
			
			setBorder(null);		
		}
	}

	protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		
	
		
		if (currentBorderColor != null) {
			g.setColor(currentBorderColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (currentDrawColor != null) {
			g.setColor(currentDrawColor);
			g.fillRect(borderSize, borderSize, getWidth() - borderSize * 2, getHeight() - borderSize * 2);
		}
		

	}
}
