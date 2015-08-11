import java.awt.event.KeyEvent;

public class ExampleArrowKeys {

	/**
	 * Demonstrates using isVirtualKeyPressed to check for arrow keys
	 */
	public static void main(String[] args) {
		Zen.create(320, 200, "stretch");
		int x = 100;
		int y = 100;
		while (Zen.isRunning()) {
			if (y > 0 && Zen.isVirtualKeyPressed(KeyEvent.VK_UP))
				y = y - 4;
			if (y < Zen.getZenHeight() - 32
					&& Zen.isVirtualKeyPressed(KeyEvent.VK_DOWN))
				y = y + 4;
			if (x > 0 && Zen.isVirtualKeyPressed(KeyEvent.VK_LEFT))
				x = x - 4;
			if (x < Zen.getZenWidth() - 32
					&& Zen.isVirtualKeyPressed(KeyEvent.VK_RIGHT))
				x = x + 4;
			Zen.drawImage("sprite1.gif", x, y);
			Zen.flipBuffer();
			Zen.sleep(8);
		}
	}

}
