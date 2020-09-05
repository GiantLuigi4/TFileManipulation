package com.tfc.files.tfile.reader;

import com.tfc.files.tfile.TFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Set;

/**
 * a simple readonly tfile browser
 */
public class Window extends JComponent implements MouseWheelListener, MouseListener {
	private final JFrame thisFrame = new JFrame("TFile Browser");
	private TFile currentFile;
	private int scroll = 0;
	private int depth = 0;
	
	/**
	 * just so that this way I can end the repaint thread when the window closes, and it doesn't halt the owner thread
	 */
	private final Thread thread = new Thread(() -> {
		while (thisFrame.isVisible()) {
			thisFrame.repaint();
		}
		Runtime.getRuntime().exit(0);
	});
	
	/**
	 * change the file that the browser is reading
	 * @param currentFile the new tfile
	 */
	public void setCurrentFile(TFile currentFile) {
		this.currentFile = currentFile;
	}
	
	/**
	 * instance a file browser
	 * @param file the file to browse
	 */
	public Window(TFile file) {
		thisFrame.setSize(600, 400);
		thisFrame.add(this);
		thisFrame.addMouseWheelListener(this);
		thisFrame.addMouseListener(this);
		currentFile = file;
	}
	
	/**
	 * start the file browser
	 */
	public void start() {
		thisFrame.setVisible(true);
		thread.start();
	}
	
	/**
	 * start the file browser
	 * @param width the width of the browser
	 * @param height the height of the browser
	 */
	public void start(int width, int height) {
		thisFrame.setSize(width, height);
		thisFrame.setVisible(true);
		thread.start();
	}
	
	/**
	 * this draws all of the files
	 * (why did I even javadoc this?)
	 * @param g the graphics instance
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int y = 30;
		TFile fileRead = currentFile;
		try {
			for (int i = 0; i < depth; i++) {
				fileRead = fileRead.getInner();
			}
		} catch (Throwable ignored) {
			fileRead.createInnerTFile();
		}
		Set<String> names = fileRead.listAllNames();
		Object[] namesArray = names.toArray();
		int num = (thisFrame.getHeight() / 15);
		try {
			if (scroll < namesArray.length) {
				for (int fi = scroll; fi < Math.min(namesArray.length, scroll + num); fi++) {
					g.setColor(Color.GRAY);
					g.fillRect(0, y, thisFrame.getWidth(), 15);
					g.setColor(Color.DARK_GRAY);
					g.fillRect(0, y, thisFrame.getWidth(), 1);
					g.fillRect(0, y + 15, thisFrame.getWidth(), 1);
					g.setColor(Color.BLACK);
					y += 15;
					g.drawString((String) namesArray[fi], 0, y - 3);
				}
			}
		} catch (Throwable ignored) {
		}
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, thisFrame.getWidth(), 15);
		g.fillRect(0, 15, thisFrame.getWidth(), 15);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, thisFrame.getWidth(), 1);
		g.fillRect(0, 15, thisFrame.getWidth(), 1);
		g.fillRect(0, 15, thisFrame.getWidth(), 1);
		g.setColor(Color.BLUE);
		g.fillRect(0, 30, thisFrame.getWidth(), 1);
		g.setColor(Color.BLACK);
		g.drawString("Go deeper", 0, 12);
		g.drawString("Go up", 0, 27);
	}
	
	/**
	 * handle scrolling
	 * @param e the scroll event
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int num = (thisFrame.getHeight() / 15);
		TFile fileRead = currentFile;
		try {
			for (int i = 0; i < depth; i++) {
				fileRead = fileRead.getInner();
			}
		} catch (Throwable ignored) {
			fileRead.createInnerTFile();
		}
		Set<String> names = fileRead.listAllNames();
		if (scroll < names.size() - num) {
			scroll += e.getWheelRotation() * -1;
		} else {
			scroll = names.size() - num;
		}
		if (scroll < 0) scroll = 0;
	}
	
	/**
	 * handle clicking
	 * @param e the mouse click event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getPoint().y <= 47) {
				depth += 1;
			} else if (e.getPoint().y <= 60) {
				depth -= 1;
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			scroll = 0;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
