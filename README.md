# ZenGraphics
ZenGraphics is single Java class that allows novice Java programmers to quickly create simple interactive programs.

# Getting started with ZenGraphics
Zen Graphics ("Zen") uses a single file "Zen.java" that you can copy and use as part of your own Java program. For example, here's a simple Java program that uses Zen Graphics to display the text Hello World and draw an image.

```Java
public class Example0HelloWorld {
	public static void main(String[] args) {
		
		Zen.drawText("Hello World!",100,200);
		Zen.drawImage("sprite1.gif", 30, 40);
		
	}
}
```
More advanced examples can specify the size of the graphics area using Zen.create. If your program just starts drawing then the default drawing area is set to 640 pixels across by 480 down. 

Zen can test for mouse clicks, key presses and special keys such as shift, control and the four arrow keys. For example, each time around the while loop below, we test to see if the arrow keys are pressed and move the stick figure up, down, left or right.
```Java
import java.awt.event.KeyEvent;

public class ExampleArrowKeys {
	public static void main(String[] args) {
		Zen.create(320, 200, "stretch");
		int x = 100;
		int y = 100;
		while (Zen.isRunning()) {
			
			if (Zen.isVirtualKeyPressed(KeyEvent.VK_UP))
				y = y - 4;
			if (Zen.isVirtualKeyPressed(KeyEvent.VK_DOWN))
				y = y + 4;
			if (Zen.isVirtualKeyPressed(KeyEvent.VK_LEFT))
				x = x - 4;
			if (Zen.isVirtualKeyPressed(KeyEvent.VK_RIGHT))
				x = x + 4;
				
			Zen.drawImage("sprite1.gif", x, y);
			Zen.flipBuffer();
			Zen.sleep(8); // Pause for 8 milliseconds
		} 
	}

}
```
The above example also uses double buffering. This is where your code creates a hidden picture. Only when 'flipBuffer' is called is it displayed on the screen. Double buffering reduces flickering because only completed pictures are presented to the user. Double buffering is automatically enabled the first time 'flipBuffer' is called.

See the example demos for more information and the source code of Zen.

# About Zen

Zen was created by Lawrence Angrave at the University of Illinois. It can run either as a standalone program or as part of a Java applet. Future versions may not support applets which will allow the code to be significantly simplified.


# License

Yes - you can use Zen in your own commercial and non-commercial projects.
ZenGraphics is provided here under the The MIT License (MIT) - see the LICENSE file for details. However you may also re-license the source code Zen.java under any open source license approved by the Open Source Initiative (http://opensource.org/licenses) and use the source code,Zen.java, under those terms. An acknowledgement of this original work by Lawrence Angrave, and a link to this github project would be appreciated but is not required.

The sprite images "sprite1.gif" and "sprite2.gif" are by Philipp Lenssen and provided under the 
"Attribution-Creative Commons License"
http://creativecommons.org/licenses/by/3.0/

The complete set of original sprites can be found here - 
http://blogoscoped.com/archive/2006-08-08-n51.html

