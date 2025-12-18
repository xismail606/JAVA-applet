Car Animation – Java Applet Project
1. Overview

This project implements a dynamic and interactive 2D car animation using the Java Applet framework. It demonstrates essential Computer Graphics and Operating System–level concepts, including:

Affine Transformations (translation, rotation, scaling)

Double Buffering for flicker-free rendering

Multithreading for smooth animation loops

Event-driven GUI interaction

A dedicated Control Panel allows real-time manipulation of the animation parameters, making the project both educational and interactive.

2. System Architecture

The application follows a clear separation of concerns and is organized into the following components:

2.1 Core Classes

CarAnimation.java (View & Controller)

Extends Applet

Manages rendering, animation logic, and painting

Implements Runnable for threading

Handles user interaction via multiple event listeners

ControlPanel.java (User Interface)

Extends Panel

Contains all UI elements such as buttons, scrollbars, checkboxes, and dropdowns

Provides real-time control over animation behavior

2.2 Supporting Files

index.html – Entry point for loading the applet

run.bat – Windows batch script to compile and launch the project

Includes UTF-8 code page setup

Displays custom ASCII art branding (APPLET, 606)

Automates compilation and execution using appletviewer

3. Technical Implementation
3.1 Graphics Rendering

The project uses Graphics2D to enable advanced rendering features:

Anti-aliasing via RenderingHints for smooth edges

GradientPaint for realistic sky, sun, and car body shading

Complex geometric shapes, including:

RoundRectangle2D for the car body

Polygon for windows

Ellipse2D for wheels and lights

3.2 Geometric Transformations

Animation behavior relies heavily on AffineTransform:

Wheel Rotation
Simulates realistic rolling using speed-based angular increments:

wheelRotation += speed * WHEEL_ROTATION_MULTIPLIER * direction;


Sun Rotation
Rotates around its center using g2d.rotate() to add background dynamics

Car Scaling & Direction
Achieved by scaling the graphics context:

g2d.scale(carScale * direction, carScale);

3.3 Animation Engine

Dedicated Animation Thread
Continuously updates object positions and triggers repaints

Double Buffering
The update() method renders all graphics to an off-screen image before drawing it to the screen, eliminating flicker common in AWT applications

4. User Interface – Control Panel

The control panel is divided into logical sections for ease of use:

Section	Component	Functionality
Top	Start / Stop / Reset	Controls animation execution
Top	Color Selector	Dynamically changes the car body color
Middle	Reverse Checkbox	Toggles the car direction
Middle	Scale Input	Resizes the car (range: 1.0 – 2.5)
Bottom	Speed Slider	Adjusts animation speed (values: 1–21)
5. Scene Composition

The visual scene is rendered in layered order to preserve depth and perspective:

Background – Sky gradient and rotating sun

Far Ground – Moving clouds and distant tree zone

Mid Ground – Trees and grass details

Foreground – Road, guardrails, and animated car

6. How to Run

To execute the project on Windows:

Run run.bat

The script:

Switches the terminal to UTF-8 (code page 65001)

Displays ASCII art banners

Compiles all .java files

Launches appletviewer with index.html

⚠️ Note: Java Applets are deprecated in modern browsers. This project is intended for educational and legacy Java environments.

7. Conclusion

The Car Animation project demonstrates how mathematical transformations, multithreading, and object-oriented design can be combined to create a smooth and interactive graphical simulation. Through robust event handling and double buffering, the application maintains visual stability while offering real-time user control, making it a strong educational example of classic Java-based computer graphics.
