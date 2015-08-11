public class Example3ColorOvalsIf {

	/*
	 * Draw some colorful ovals.
	 */
	public static void main(String[] args) {
		long lastClickTime = 0;

		// It's polite to ask Zen when to close down
		while (Zen.isRunning()) {

			long clickTime = Zen.getMouseClickTime();
			int x = Zen.getMouseX();
			int y = Zen.getMouseY();
			if (clickTime != 0)
				Zen.fillOval(x - 16, y - 16, 32, 32);

			if (clickTime != lastClickTime) {
				lastClickTime = clickTime;
				Zen.setColor(x, y, x + y);
			}
		}
	}
}
