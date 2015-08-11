public class Example2SpriteMoveFlipBuffer {

	/**
	 * Drawing a sprite using a two buffers.
	 * When the scene is ready to be displayed, call 'flipBuffer'
	 * to present the new image to the user.
	 */
	public static void main(String[] args) {
		
		// With flipBuffer: Changes are written to a secret background buffer first
		// When flipBuffer is called, the background buffer and the foreground buffer are swapped.
		
		// When flipBuffer is called one (or more times) 
		// Zen stops painting immediately to the screen and instead uses a behind-the-scenes buffer
		// to assemble the next complete image to display.
		int x = 100;
		while (true) {
			Zen.drawImage("sprite1.gif", x, 200);
			
			Zen.flipBuffer();
			Zen.sleep(50); // 50 milliseconds (1/20th second)
			x = x + 5;
		} // while
	} // main
}
