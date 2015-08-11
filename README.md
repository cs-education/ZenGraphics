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

See the example demos, the source code of Zen and the introductory ZenGuide (pdf) for more information.

# About Zen

Zen was created by Lawrence Angrave at the University of Illinois. It can run either as a standalone program or as part of a Java applet. Future versions may not support applets which will allow the code to be significantly simplified.

The Zen Guide (ZenGuide2013.pdf) was created by a student,John Lee, at the University of Illinois.

# License information

Yes - you can use Zen source code in your own commercial and non-commercial projects.

ZenGraphics is provided here under the The MIT License (MIT) - see the LICENSE file for details. However you may also re-license the source code Zen.java under any open source license approved by the Open Source Initiative (http://opensource.org/licenses) and use the source code,Zen.java, under those terms. An acknowledgement of this original work by Lawrence Angrave, and a link to this github project would be appreciated but is not required.

The sprite images "sprite1.gif" and "sprite2.gif" are by Philipp Lenssen and provided under the 
"Attribution-Creative Commons License"
http://creativecommons.org/licenses/by/3.0/

The complete set of original sprites can be found here - 
http://blogoscoped.com/archive/2006-08-08-n51.html

Generated example web pages use "j2h.jar" to create colorized source code (see publish/). This library is licensed under the GPL (see j2h-license-gpl.txt) but is not required for Zen itself

# FAQ

* Can I modify or extend Zen?
Yes - of course! However we politely ask that you name your creation something other than Zen and include a reference back to this original project. 

* I looked Zen's source code. Why does Zen use static (class) methods then use thread local storage to call instance methods?
This is internal in complexity to allow novices to create extremely simple programs that still work when published to the web. A web page can have more than applet running and each applet will be identified by its own thread. Eventually this convoluted implementation can be removed when we no longer want to support applets.

* Can I use Zen in multithreaded programs?
Yes if all of your Zen calls are from the same thread or you change Zen source code to aways return the same Zen object.

* How do I publish my awesome program as a web applet?

Caution: You will need a web browser that supports Java applets but most people today disable Java applets because they are a security risk.

There is an ant build script 'buildWebPages.xml' inside the publish/ subdirectory that will bundle up all of the files into a single "jar" file. This file can create all of the example demos and can be modified to create additional web applets and html resources. The jar file is given a unique name to ensure that web browsers don't use an old version when the page is reloaded. By the way, running ant scripts is easy if you are using Eclipse - just right click on them and select Run As ant.
