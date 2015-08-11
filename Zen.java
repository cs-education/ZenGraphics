import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Easy graphics for Java students and programmers interested in creating Java
 * graphical programs quickly. The more astute programmer may notice some odd
 * design choices - The design decisions (such as using class methods) is there
 * to simplify common use cases. This code is supplied 'as is' and no warranty
 * is provided.
 * 
 * This code is free to use and is provided 'as is' without warranty or
 * guarantee for fitness for any particular purpose. This code is copyrighted.
 * You may use it in any way provided you preserve the following copyright
 * statement and included it in copyright information about your program.
 * "Zen Graphics" Copyright Lawrence Angrave 2010.
 * 
 * Startup notes: If started as an applet the applet container automatically
 * calls init() and typically not in the GUI thread. The init method notices
 * that 'instance' is null and creates a new background thread to call the
 * 'main' method.
 * 
 * If started from a main method. The user's program will at some point try to
 * draw something. This causes getInstance() to be invoked which lazily creates
 * an object instance and calls init Todos: If the applet is stopped we should
 * close the main thread gracefully.
 * 
 * @author angrave
 * 
 */
@SuppressWarnings("serial")
public class Zen extends JApplet {

	public static final Dimension DEFAULT_SIZE = new Dimension(640, 480);
	public static final String DEFAULT_OPTIONS = "";

	public static ZenInstance create(int width, int height, String options) {
		if (!mustBeAnWebApplet)
			mustBeAnApplication = true;
		synchronized (Zen.class) {
			ZenInstance instance = instanceMap.get();
			if (instance == null) { // no instance set for this thread
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// System.err.println("Creating Instance");
				Zen zen = new Zen();
				zen.bufferSize = new Dimension(width, height);
				zen.bufferOptions = options;

				instanceMap.set(zen.master);
				frame.getContentPane().add(zen);
				frame.getContentPane().setSize(zen.getSize());
				frame.getContentPane().setMinimumSize(zen.getSize());
				// frame.getContentPane().setIgnoreRepaint(true);
				zen.init();
				frame.pack();
				frame.setVisible(true);

				zen.start();
				return zen.master;
			}
			return instance;
		}

	}

	public static String getAboutMessage() {
		return "Zen Graphics (version 0.1) Copyright Lawrence Angrave, 2010";
	}

	public static void waitForClick() {
		getInstanceFromThread().waitForClick();
	}

	public static int getZenWidth() {
		return getInstanceFromThread().getZenWidth();
	}

	public static int getZenHeight() {
		return getInstanceFromThread().getZenHeight();
	}

	public static int getMouseClickX() {
		return getInstanceFromThread().getMouseClickX();
	}

	public static int getMouseClickY() {
		return getInstanceFromThread().getMouseClickY();
	}

	public static long getMouseClickTime() {
		return getInstanceFromThread().getMouseClickTime();
	}

	public static void setEditText(String s) {
		getInstanceFromThread().setEditText(s);
	}

	public static String getEditText() {
		return getInstanceFromThread().getEditText();
	}

	public static int getMouseButtonsAndModifierKeys() {
		return getInstanceFromThread().getMouseButtonsAndModifierKeys();
	}

	public static int getMouseX() {
		return getInstanceFromThread().getMouseX();
	}

	public static int getMouseY() {
		return getInstanceFromThread().getMouseY();
	}

	public static void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception ignored) {
		}
	}

	public static boolean isKeyPressed(char key) {
		return getInstanceFromThread().isKeyPressed(key);
	}

	public static boolean isVirtualKeyPressed(int keyCode) {
		return getInstanceFromThread().isVirtualKeyPressed(keyCode);
	}

	public static boolean isRunning() {
		return getInstanceFromThread().isRunning();
	}

	public static Graphics2D getBufferGraphics() {
		return getInstanceFromThread().getBufferGraphics();
	}

	public static void drawImage(String filename, int x, int y) {
		getInstanceFromThread().drawImage(filename, x, y);
	}

	public static void drawLine(int x1, int y1, int x2, int y2) {
		getInstanceFromThread().drawLine(x1, y1, x2, y2);
	}

	public static void drawText(String text, int x, int y) {
		getInstanceFromThread().drawText(text, x, y);

	}

	public static void drawArc(int x, int y, int width, int height,
			int startAngle, int arcAngle) {
		getInstanceFromThread().drawArc(x, y, width, height, startAngle,
				arcAngle);
	}

	public static void fillOval(int minX, int minY, int width, int height) {
		getInstanceFromThread().fillOval(minX, minY, width, height);
	}

	public static void fillRect(int x1, int y1, int width, int height) {
		getInstanceFromThread().fillRect(x1, y1, width, height);
	}

	public static void setColor(int red, int green, int blue) {
		getInstanceFromThread().setColor(red, green, blue);
	}

	public static int bound(int value, int min, int max) {
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}

	public static Font setFont(String fontname) {
		return getInstanceFromThread().setFont(fontname);
	}

	public static Image getCachedImage(String filename) {
		return getInstanceFromThread().getCachedImage(filename);
	}

	public static void flipBuffer() {
		getInstanceFromThread().flipBuffer();
	}

	/*
	 * ----------------- Instance Methods -------------------------
	 */
	class ZenInstance {
		public void waitForClick() {
			long t = mouseClickTime;
			// Todo: remove polling and use synchronization wait lock
			while (isRunning && t == mouseClickTime)
				sleep(250);
		}

		public int getZenWidth() {
			return bufferSize.width;
		}

		public int getZenHeight() {
			return bufferSize.height;
		}

		public int getMouseClickX() {
			return mouseClickX;
		}

		public int getMouseClickY() {
			return mouseClickY;
		}

		public long getMouseClickTime() {
			return mouseClickTime;
		}

		public void setEditText(String s) {
			editText = new StringBuilder(s);
		}

		public String getEditText() {
			return editText.toString();
		}

		public int getMouseButtonsAndModifierKeys() {
			return mouseButtonsAndModifierKeys;
		}

		public int getMouseX() {
			return mouseX;
		}

		public int getMouseY() {
			return mouseY;
		}

		public boolean isKeyPressed(char key) {
			return key >= 0 && key < keyPressed.length ? keyPressed[key]
					: false;
		}

		public boolean isVirtualKeyPressed(int keyCode) {
			return keyCode >= 0 && keyCode < virtualKeyPressed.length ? virtualKeyPressed[keyCode]
					: false;
		}

		public boolean isRunning() {
			return isRunning;
		}

		public Graphics2D getBufferGraphics() {
			// getSingleton(); // ensure instance created
			while (g == null) {
				System.err
						.println("Odd... graphics not yet ready! Sleeping...");
				sleep(1000); // race-condition hack ; should never happen if the
				// container is correctly implemented
			}
			return g;
		}

		public void drawImage(String filename, int x, int y) {
			Graphics2D g = getBufferGraphics();
			Image img = getCachedImage(filename);
			if (img != null)
				g.drawImage(img, x, y, null);
			else
				g.drawString(filename + "?", x, y);
			if (paintImmediately)
				paintWindowImmediately();
		}

		public void drawLine(int x1, int y1, int x2, int y2) {
			getBufferGraphics().drawLine(x1, y1, x2, y2);
			if (paintImmediately)
				paintWindowImmediately();
		}

		public void drawText(String text, int x, int y) {
			getBufferGraphics().drawString(text, x, y);
			if (paintImmediately)
				paintWindowImmediately();
		}

		public void drawArc(int x, int y, int width, int height,
				int startAngle, int arcAngle) {
			getBufferGraphics().drawArc(x, y, width, height, startAngle,
					arcAngle);
			if (paintImmediately)
				paintWindowImmediately();
		}

		public void fillOval(int minX, int minY, int width, int height) {
			getBufferGraphics().fillOval(minX, minY, width, height);
			if (paintImmediately)
				paintWindowImmediately();
		}

		public void fillRect(int x1, int y1, int width, int height) {
			getBufferGraphics().fillRect(x1, y1, width, height);
			if (paintImmediately)
				paintWindowImmediately();
		}

		public void setColor(int red, int green, int blue) {
			currentColor = new Color(bound(red, 0, 255), bound(green, 0, 255),
					bound(blue, 0, 255));
			getBufferGraphics().setColor(currentColor);
		}

		public Font setFont(String font) {
			currentFont = Font.decode(font);
			getBufferGraphics().setFont(currentFont);
			return currentFont;
		}

		public Image getCachedImage(String filename) {
			Image img = nameToImage.get(filename);
			if (img != null)
				return img;
			try {
				InputStream is = Zen.class.getResourceAsStream(filename);
				img = ImageIO.read(is);
				is.close();
				nameToImage.put(filename, img);
				return img;
			} catch (Exception ex) {
				// System.err.println("Can't load '" + filename + "' : "
				// + ex.getMessage());
				return null;
			}
		}

		public void flipBuffer() {
			// Both flipBuffer and portions of paint() are synchronized
			// on the class object to ensure
			// that both cannot execute at the same time.
			paintImmediately = false; // user has called flipBuffer at least
			// once
			// getSingleton();
			synchronized (Zen.this) {
				Image temp = backImageBuffer;
				backImageBuffer = frontImageBuffer;
				frontImageBuffer = temp;

				if (g != null)
					g.dispose();
				paintWindowImmediately(); // paint to Video

				g = (Graphics2D) backImageBuffer.getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, backImageBuffer.getWidth(null),
						backImageBuffer.getHeight(null));
				g.setColor(currentColor);
				g.setFont(currentFont);
			}
		}

		void createBuffers(int width, int height, String options) {
			if (g != null)
				g.dispose();
			if (frontImageBuffer != null)
				frontImageBuffer.flush();
			if (backImageBuffer != null)
				backImageBuffer.flush();
			options = options != null ? options.toLowerCase() : "";
			bufferSize = new Dimension(width, height);
			stretchToFit = options.contains("stretch");

			// if buffers are requested _after_ the window has been realized
			// then faster volatile images are possible
			try {
				backImageBuffer = createVolatileImage(width, height);
				frontImageBuffer = createVolatileImage(width, height);
			} catch (Exception ignored) {

			}
			// as a fall-back we can still use slower BufferedImage
			if (frontImageBuffer == null) {
				// System.err.println("Creating BufferedImage buffers");
				backImageBuffer = new BufferedImage(bufferSize.width,
						bufferSize.height, BufferedImage.TYPE_INT_RGB);
				frontImageBuffer = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
			}
			master.flipBuffer();// set up graphics, including font and color
			// state
			paintImmediately = true; // actually, user has not yet called
			// flipBuffer
		}

	}; // End class ZenMaster

	/*
	 * --------------------- IMPLEMENTATION ----------------------
	 */
	private static ThreadLocal<ZenInstance> instanceMap = new ThreadLocal<ZenInstance>();

	private static synchronized ZenInstance getInstanceFromThread() {
		ZenInstance instance = instanceMap.get();
		return instance != null ? instance : create(DEFAULT_SIZE.width,
				DEFAULT_SIZE.height, DEFAULT_OPTIONS);
	}

	private static boolean mustBeAnApplication; // true if create called init
	private static boolean mustBeAnWebApplet; // true if init called before
	// create
	private ZenInstance master = new ZenInstance();

	private Graphics2D g;
	private Image backImageBuffer, frontImageBuffer;
	private Map<String, Image> nameToImage = Collections
			.synchronizedMap(new HashMap<String, Image>());

	private boolean stretchToFit;
	private boolean[] keyPressed = new boolean[256];
	private boolean[] virtualKeyPressed = new boolean[1024];

	private int mouseX, mouseY, mouseClickX, mouseClickY;
	private long mouseClickTime;
	private StringBuilder editText = new StringBuilder();
	private Dimension bufferSize = new Dimension(DEFAULT_SIZE);
	private String bufferOptions = DEFAULT_OPTIONS;
	private Color currentColor = Color.WHITE;
	private Font currentFont = Font.decode("Times-18");
	private int mouseButtonsAndModifierKeys;
	private boolean isRunning = true;
	private Thread mainThread;
	private int paintAtX, paintAtY, windowWidth, windowHeight;
	protected boolean paintImmediately;

	public Dimension getMinimumSize() {
		return bufferSize;
	}

	public final Dimension getPreferredSize() {
		return getMinimumSize();
	}

	// JApplet methods
	public final void init() {
		if (!mustBeAnApplication)
			mustBeAnWebApplet = true;

		instanceMap.set(master);

		setSize(bufferSize);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				addMouseListener(mouseListener);
				addMouseMotionListener(mouseMotionListener);
				addKeyListener(keyListener);
				setFocusTraversalKeysEnabled(false);
				setFocusable(true);
				setVisible(true);
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

			} // run
		});
	}

	@SuppressWarnings("deprecation")
	public final void stop() {
		isRunning = false;
		if (mainThread == null)
			return;
		mainThread.interrupt();
		sleep(500);
		if (mainThread.isAlive())
			mainThread.stop();
		mainThread = null;
	}

	@Override
	public final void start() {
		master
				.createBuffers(bufferSize.width, bufferSize.height,
						bufferOptions);
		isRunning = true;
		if (mustBeAnWebApplet) {
			mainThread = new Thread("main") {
				public void run() {
					try {
						String paramKey = "zen-main-class";
						String classNameToRunForApplet = getParameter(paramKey);
						if (classNameToRunForApplet == null) {
							String mesg = paramKey
									+ " parameter is not set; where is your main method!";
							System.err.println(mesg);
							setFont("Courier-12");
							drawText(mesg, 0, 10);
							return;
						}
						Class<?> clazz = Class.forName(classNameToRunForApplet);
						String[] argValue = new String[0];
						Class<?>[] argTypes = { argValue.getClass() };
						Method main = clazz.getMethod("main", argTypes);
						// System.err.println(clazz + ".main()...");
						instanceMap.set(Zen.this.master);
						main.invoke(null, new Object[] { argValue });

					} catch (ThreadDeath ignored) {
					} catch (Exception ex) {
						System.err.println("Exception:" + ex.getMessage());
						ex.printStackTrace();
						setFont("Courier-12");
						drawText(ex.toString(), 0, 12);
					} finally {
						instanceMap.remove();
					}

				} // end method
			};
			mainThread.start();
		}
	}

	public final void destroy() {
		super.destroy();
	}

	@Override
	public void update(Graphics windowGraphics) {
		paint(windowGraphics);
	}

	@Override
	public void paint(Graphics windowGraphics) {
		if (windowGraphics == null)
			return;
		windowWidth = getWidth();
		windowHeight = getHeight();

		if (frontImageBuffer == null) {
			// no image to display
			windowGraphics.clearRect(0, 0, windowWidth, windowHeight);
			return;
		}
		synchronized (Zen.class) {
			Image image = paintImmediately ? backImageBuffer : frontImageBuffer;
			if (stretchToFit) {
				paintAtX = paintAtY = 0;
				windowGraphics.drawImage(image, 0, 0, windowWidth,
						windowHeight, this);
			} else { // Blacken unused sides
				int x = windowWidth - bufferSize.width;
				int y = windowHeight - bufferSize.height;
				paintAtX = x / 2;
				paintAtY = y / 2;
				windowGraphics.setColor(Color.BLACK);
				// Notes: Some of the +1's may be unnecessary.
				// Notes: Actually there's some overlap in the 4 corners that
				// could
				// be removed
				if (y > 0) {
					windowGraphics.fillRect(0, 0, windowWidth + 1, paintAtY);
					windowGraphics.fillRect(0, windowHeight - paintAtY - 1,
							windowWidth + 1, paintAtY + 1);
				}
				if (x > 0) {
					windowGraphics.fillRect(0, 0, paintAtX + 1,
							windowHeight + 1);
					windowGraphics.fillRect(windowWidth - paintAtX - 1, 0,
							paintAtX + 1, windowHeight + 1);
				}
				windowGraphics.drawImage(image, paintAtX, paintAtY, this);
			}
		}
	} // paint

	private void paintWindowImmediately() {
		paint(getGraphics());
	}

	private KeyListener keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			char c = e.getKeyChar(); // may be CHAR_UNDEFINED
			mouseButtonsAndModifierKeys = e.getModifiersEx();
			if (c >= 0 && c < keyPressed.length)
				keyPressed[c] = true;
			int vk = e.getKeyCode();
			if (vk >= 0 && vk < virtualKeyPressed.length)
				virtualKeyPressed[vk] = true;
		}

		public void keyReleased(KeyEvent e) {
			char c = e.getKeyChar(); // may be CHAR_UNDEFINED
			mouseButtonsAndModifierKeys = e.getModifiersEx();
			if (c >= 0 && c < keyPressed.length)
				keyPressed[c] = false;
			int vk = e.getKeyCode();
			if (vk >= 0 && vk < virtualKeyPressed.length)
				virtualKeyPressed[vk] = false;
		}

		public void keyTyped(KeyEvent e) {
			char typed = e.getKeyChar();
			if (!Character.isISOControl(typed))
				editText.append(typed);
			else if (typed == 8 && editText.length() > 0)
				editText.deleteCharAt(editText.length() - 1);
		}
	}; // KeyListener
	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent me) {
			if (windowWidth == 0 || windowHeight == 0)
				return; // no display window yet
			mouseClickX = (stretchToFit ? (int) (0.5 + me.getX()
					* bufferSize.width / (double) windowWidth) : me.getX()
					- paintAtX);
			mouseClickY = (stretchToFit ? (int) (0.5 + me.getY()
					* bufferSize.height / (double) windowHeight) : me.getY()
					- paintAtY);
			mouseClickTime = me.getWhen();
		}

	}; // MouseListener
	private MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent me) {
			if (windowWidth == 0 || windowHeight == 0)
				return; // no display window yet
			mouseX = (stretchToFit ? (int) (0.5 + me.getX() * bufferSize.width
					/ (double) windowWidth) : me.getX() - paintAtX);
			mouseY = (stretchToFit ? (int) (0.5 + me.getY() * bufferSize.height
					/ (double) windowHeight) : me.getY() - paintAtY);
			mouseButtonsAndModifierKeys = me.getModifiersEx();
		}

		public void mouseDragged(MouseEvent e) {
			mouseListener.mouseClicked(e);
			mouseMoved(e);
		}
	}; // MouseMotionListener

};// End of class