package com.thanico.ponglwjgl;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.thanico.ponglwjgl.processing.PongBall;
import com.thanico.ponglwjgl.processing.PongCollisionManager;
import com.thanico.ponglwjgl.processing.PongHUD;
import com.thanico.ponglwjgl.processing.PongPaddle;
import com.thanico.ponglwjgl.ui.FuckingSimpleLwjglText;

public class PongApp {
	/**
	 * The window handle
	 */
	private long window;

	/**
	 * Pong paddles and ball
	 */
	private PongPaddle leftPaddle;
	private PongPaddle rightPaddle;
	private PongBall pongBall;

	private PongHUD pongHUD;

	private FuckingSimpleLwjglText sflt;
	/**
	 * Collision management
	 */
	private PongCollisionManager pcm;

	/**
	 * Main runner
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new PongApp().run(400, 400, "Pong LWJGL !");
	}

	/**
	 * Application startup
	 */
	public void run(int width, int height, String applicationName) {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init(width, height, applicationName);

		sflt = new FuckingSimpleLwjglText(width, height);

		leftPaddle = new PongPaddle(-0.97f, 0.80f);
		rightPaddle = new PongPaddle(0.97f, 0.80f);
		pongBall = new PongBall(-0.025f, -0.025f);
		pcm = new PongCollisionManager(leftPaddle, rightPaddle, pongBall);
		pongHUD = new PongHUD();

		setKeysCallback();
		loop();

		//
		sflt.destroy();

		GL.setCapabilities(null);

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	/**
	 * Graphical initialization
	 * 
	 * @param applicationName
	 * @param height
	 * @param width
	 */
	private void init(int width, int height, String applicationName) {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, applicationName, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	/**
	 * Create the keys callback
	 */
	private void setKeysCallback() {
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			// Setup a key callback. It will be called every time a key is pressed, repeated
			// or released.
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			}
			// Action key for paddles
			else if (action != GLFW_RELEASE) {
				switch (key) {
				case GLFW_KEY_E:
					leftPaddle.moveTop(PongPaddle.PADDLE_SPEED_Y);
					break;
				case GLFW_KEY_D:
					leftPaddle.moveBottom(PongPaddle.PADDLE_SPEED_Y);
					break;
				case GLFW_KEY_UP:
					rightPaddle.moveTop(PongPaddle.PADDLE_SPEED_Y);
					break;
				case GLFW_KEY_DOWN:
					rightPaddle.moveBottom(PongPaddle.PADDLE_SPEED_Y);
					break;
				}
			}
		});
	}

	/**
	 * Graphical drawing
	 */
	private void loop() {

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		long time = System.nanoTime();
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			long t = System.nanoTime();
			float dt = (float) ((t - time) / 1000000000.0);
			time = t;
			sflt.loop(dt);

			leftPaddle.draw();
			rightPaddle.draw();
			if (pcm.changeDirectionIfCollision()) {
				// if leftside was last touch; then rightside has lost
				// so add point to leftside
				pongHUD.addPoint(pcm.isLeftsideLastTouch());
			}
			pongBall.updatePositionFromDirection();
			pongBall.draw();
			pongHUD.draw();

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
}
