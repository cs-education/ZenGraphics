public class ExampleNearlyAllMethods {

	/**
	 * Demonstrates using most of the public methods of Zen.
	 */
	public static void main(String[] args) {
		Zen.setColor(255, 255, 255); // White
		Zen.setFont("Times-40");
		Zen.drawText("Click!", 20, 40);
		
		Zen.waitForClick();
		
		Zen.setColor(0, 255, 255); // Orange
		Zen.setFont("Courier-12");
		Zen.drawText(Zen.getAboutMessage(), 0, 400);
		
		Zen.flipBuffer();
		Zen.sleep(2000);

		Zen.drawLine(300, 150, 150, 300); 
		// (300,150) to (150,300)
		Zen.drawArc(400, 400, 50, 50, 30, 150);
		// drawArc(x, y, width, height,startAngle, arcAngle);
		Zen.fillOval(100, 20, 40, 40);
		// fillOval(minX, minY, width, height)
		Zen.fillRect(5, 10, 5, 5);
		// fillRect(minX, minY, width, height)

		Zen.flipBuffer();
		Zen.sleep(1000);

		Zen.setEditText("Type and Click");
		while (Zen.isRunning()) {
			String text = Zen.getEditText();
			Zen.drawText(text, 100, 100);
			
			Zen.fillRect(Zen.getMouseX() - 16, Zen.getMouseY() - 16, 32, 32);
			
			long clickTime = Zen.getMouseClickTime();
			String mesg = "Clicked at " + clickTime;
			Zen.drawText(mesg, Zen.getMouseClickX(), Zen.getMouseClickY());
			
			Zen.flipBuffer();
		} // while
	} // main
}
