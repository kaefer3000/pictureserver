package edu.kit.aifb.ls3.pictureserver;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DerServletContextListener implements ServletContextListener {

	final static Logger _log = Logger.getLogger(DerServletContextListener.class.getName());

	ServletContext _ctx;

	enum PictureServerAttributes {
		FENSTERFRAME, INHALTSPANEL
	}

	public class ImagePanel extends JPanel {

		private static final long serialVersionUID = -6041044479667736419L;

		private BufferedImage _image;

		public ImagePanel() {

			_image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

			Graphics2D ig2 = _image.createGraphics();

			Font font = new Font("TimesRoman", Font.BOLD, 20);
			ig2.setFont(font);
			String message = "Tobias' wicked picture server!!1";
			FontMetrics fontMetrics = ig2.getFontMetrics();
			int stringWidth = fontMetrics.stringWidth(message);
			int stringHeight = fontMetrics.getAscent();
			ig2.setPaint(Color.black);
			ig2.drawString(message, (500 - stringWidth) / 2, 500 / 2 + stringHeight / 4);

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(_image, 0, 0, null);
		}

		public synchronized boolean render(InputStream is) throws IOException {
			BufferedImage bi = ImageIO.read(is);

			if (bi == null)
				return false;
			else {
				_image = bi;
				repaint();
				return true;
			}
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		JFrame fenster = new JFrame();
		fenster.setSize(600, 600);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel inhalt = new ImagePanel();
		fenster.add(inhalt);
		fenster.setVisible(true);

		_ctx = sce.getServletContext();
		_ctx.setAttribute(PictureServerAttributes.INHALTSPANEL.name(), inhalt);
		_ctx.setAttribute(PictureServerAttributes.FENSTERFRAME.name(), fenster);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JFrame f = (JFrame) _ctx.getAttribute(PictureServerAttributes.FENSTERFRAME.name());
		f.setVisible(false);
	}

}
