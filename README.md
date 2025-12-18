1. Introduction
This project implements a dynamic, interactive 2D animation of a car moving through a scenic landscape using the Java Applet framework. The system demonstrates core Computer Graphics concepts including Affine Transformations (rotation, scaling, translation), Double Buffering for flicker-free rendering, and Multi-threading for smooth animation loops. The application features a comprehensive GUI (Control Panel) allowing real-time modification of simulation parameters.
2. System Architecture
The application is structured into two primary classes and supporting files:
1.	CarAnimation.java (The View & Controller):
o	Inherits from Applet.
o	Handles the main rendering loop, animation logic, and graphics painting.
o	Implements Runnable for threading and various Event Listeners for interactivity.
2.	ControlPanel.java (The User Interface):
o	Inherits from Panel.
o	Contains all UI components (Buttons, Scrollbars, Choices) to manipulate the animation state.
3.	Execution Scripts:
o	index.html: The web entry point for the Applet.
o	run.bat: A custom batch script with ASCII art branding ("606") to compile and launch the viewer.

3. Technical Implementation Details
3.1. Graphics Rendering
The project utilizes Graphics2D to enable advanced rendering features:
•	Anti-Aliasing: Enabled via RenderingHints to smooth the edges of shapes.
•	Gradient Paints: Used for the sky (blue gradient), sun (yellow-orange), and car body to create depth.
•	Complex Shapes: The car is constructed using RoundRectangle2D, Polygon (for windows), and Ellipse2D (for wheels/lights).
3.2. Geometric Transformations
The animation relies heavily on AffineTransform to manipulate objects without redrawing them pixel-by-pixel:
•	Wheel Rotation: The wheels rotate based on the car's speed and direction. The rotation logic calculates angles based on a multiplier to simulate realistic rolling: wheelRotation += speed * WHEEL_ROTATION_MULTIPLIER * direction.
•	Sun Rotation: The sun rotates around its center using g2d.rotate() to create a dynamic background effect.
•	Car Scaling & Direction: The car's size and facing direction are controlled by scaling the graphics context: g2d.scale(carScale * direction, carScale).
3.3. Animation Engine
•	Threading: A dedicated Thread runs the run() method, which continuously updates positions and requests repaints.
•	Double Buffering: To prevent screen flickering (a common issue in AWT), the update() method is overridden to draw to an off-screen image buffer first, then render the complete image to the screen at once.
4. User Interface (Control Panel)
The ControlPanel class provides a user-friendly interface divided into three sections:
Section	Component	Functionality
Top	Start/Stop/Reset	Controls the animation thread state.
Top	Color Choice	A dropdown menu to change the car body color dynamically.
Middle	Reverse Checkbox	Toggles the integer sign of the direction variable.
Middle	Scale Input	Accepts text input (1.0 - 2.5) to resize the car.
Bottom	Speed Slider	A Scrollbar adjusting the animation delay/step size (Values 1-21).

5. Visual Composition
The scene is composed of layers drawn in a specific order to maintain perspective:
1.	Background: Sky gradient and Sun (rotating rays).
2.	Farground: Moving clouds (ellipses) and distant tree zone.
3.	Midground: Detailed trees (trunks and foliage) and grass.
4.	Foreground: The Road, guardrails, and the Car itself.
6. How to Run
The project includes a unified batch script for Windows environments:
1.	Execute run.bat.
2.	The script changes the code page to 65001 (UTF-8) and displays the "APPLET" and "606" ASCII art banners.
3.	It automatically compiles all .java files in the src directory.
4.	It launches appletviewer pointing to index.html.
7. Conclusion
The "Car Animation" project successfully demonstrates the integration of mathematical transformations with object-oriented programming to create a fluid, interactive graphical simulation. It handles user input robustly through event listeners and ensures visual stability through double buffering.
