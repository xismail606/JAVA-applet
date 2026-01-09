<!-- ===================== HEADER ===================== -->
<div align="center">
  <img 
    src="https://capsule-render.vercel.app/api?type=waving&color=gradient&text=Car%20Animation&height=140&section=header"
    alt="Car Animation Header"
    width="100%"
  />
</div>

---

<!-- ===================== TITLE ===================== -->
<h1 align="center"> 🚗 Car Animation – Java Applet Project </h1>

<p align="center">
A dynamic and interactive <strong>2D Car Animation</strong> built using the <strong>Java Applet</strong> framework.<br>
The project demonstrates core concepts of <strong>Computer Graphics</strong> and <strong>Operating Systems</strong>.
</p>

---

<!-- ===================== OVERVIEW ===================== -->
<h2 align="center"> 📌 Overview </h2>

<p align="center">
This project implements a real-time animated car simulation using classic Java AWT and Applet technologies.
It focuses on rendering quality, smooth animation, and user interaction.
</p>

<pre><strong>
core_concepts:
  - Affine Transformations
  - Double Buffering
  - Multithreading
  - Event-Driven GUI
purpose:
  - Educational
  - Computer Graphics Demonstration
  - Legacy Java Applet Example
</strong></pre>

---

<!-- ===================== ARCHITECTURE ===================== -->
<h2 align="center"> 🏗️ System Architecture </h2>

<p align="center">
The application follows a clear separation of concerns with modular components.
</p>

<h3>2.1 Core Classes</h3>

<pre><strong>
CarAnimation.java
  - Extends Applet
  - Handles rendering & animation logic
  - Implements Runnable (multithreading)
  - Manages event listeners

ControlPanel.java
  - Extends Panel
  - Contains UI controls (buttons, sliders, checkboxes)
  - Provides real-time animation control
</strong></pre>

<h3>2.2 Supporting Files</h3>

<pre><strong>
index.html
  - Entry point for loading the applet

run.bat
  - Windows automation script
  - Sets UTF-8 code page (65001)
  - Displays ASCII art branding (APPLET, 606)
  - Compiles & launches using appletviewer
</strong></pre>

---

<!-- ===================== GRAPHICS ===================== -->
<h2 align="center"> 🎨 Graphics & Rendering </h2>

<p align="center">
Rendering is handled using <code>Graphics2D</code> for advanced visual quality.
</p>

<ul>
  <li>Anti-aliasing via <code>RenderingHints</code></li>
  <li><code>GradientPaint</code> for sky, sun, and car body shading</li>
  <li>Complex shapes:
    <ul>
      <li><code>RoundRectangle2D</code> – Car body</li>
      <li><code>Polygon</code> – Windows</li>
      <li><code>Ellipse2D</code> – Wheels & lights</li>
    </ul>
  </li>
</ul>

---

<!-- ===================== TRANSFORMATIONS ===================== -->
<h2 align="center"> 🔄 Geometric Transformations </h2>

<p align="center">
The animation relies heavily on <code>AffineTransform</code>.
</p>

<pre><strong>
Wheel Rotation:
  wheelRotation += speed * WHEEL_ROTATION_MULTIPLIER * direction

Sun Rotation:
  g2d.rotate(angle, centerX, centerY)

Car Scaling & Direction:
  g2d.scale(carScale * direction, carScale)
</strong></pre>

These transformations provide realism and dynamic behavior.

---

<!-- ===================== ANIMATION ===================== -->
<h2 align="center"> ⚙️ Animation Engine </h2>

<ul>
  <li><strong>Dedicated Animation Thread</strong> for smooth updates</li>
  <li><strong>Double Buffering</strong> using off-screen rendering</li>
  <li>Prevents flickering common in AWT applications</li>
</ul>

---

<!-- ===================== UI ===================== -->
<h2 align="center"> 🎛️ Control Panel </h2>

<p align="center">
A dedicated control panel enables real-time interaction.
</p>

<pre><strong>
Controls:
  - Start / Stop / Reset animation
  - Dynamic car color selection
  - Reverse direction toggle
  - Car scale control (1.0 – 2.5)
  - Speed slider (1 – 21)
</strong></pre>

---

<!-- ===================== SCENE ===================== -->
<h2 align="center"> 🌄 Scene Composition </h2>

<p align="center">
The scene is rendered in layered order to maintain depth.
</p>

<pre><strong>
Rendering Layers:
  1. Background – Sky gradient & rotating sun
  2. Far Ground – Clouds & distant trees
  3. Mid Ground – Trees & grass
  4. Foreground – Road, guardrails & animated car
</strong></pre>

---

<!-- ===================== RUN ===================== -->
<h2 align="center"> ▶️ How To Run </h2>

<p align="center">
This project is intended for <strong>Windows</strong> and legacy Java environments.
</p>

<pre><strong>
1. Run run.bat
2. Script sets UTF-8 code page
3. Compiles all .java files
4. Launches appletviewer with index.html
</strong></pre>

<p align="center">
⚠️ <strong>Note:</strong> Java Applets are deprecated in modern browsers.<br>
This project is for educational and legacy use only.
</p>

---

<!-- ===================== CONCLUSION ===================== -->
<h2 align="center"> 🧠 Conclusion </h2>

<p align="center">
The Car Animation project demonstrates how mathematical transformations,
multithreading, and object-oriented design can be combined to create
a smooth and interactive graphical simulation.
</p>

<p align="center">
It serves as a strong educational example of classic Java-based
computer graphics and animation techniques.
</p>

---

<!-- ===================== AUTHOR ===================== -->
<h2 align="center"> 👤 Author </h2>

<p align="center">
<strong>x606</strong><br>
</p>

---

<!-- ===================== FOOTER ===================== -->
<div align="center">
  <img 
    src="https://capsule-render.vercel.app/api?type=waving&color=gradient&height=100&section=footer"
    width="100%"
  />
</div>
