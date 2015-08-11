public class Example4Paint {

	/**
	 * A simple paint program. Press any modifier key (e.g shift) or mouse
	 * button to paint.
	 */
	public static void main(String[] args) {
		Zen.create(320, 200, "stretch");
		while (Zen.isRunning()) {
			int x = Zen.getMouseX();
			int y = Zen.getMouseY();
			if (Zen.getMouseButtonsAndModifierKeys() != 0) {
				double x2 = x - 25 + 50 * Math.random();
				double y2 = y - 25 + 50 * Math.random();
				Zen.fillRect((int) x2, (int) y2, 1, 1);
			}
		}
	}
}
