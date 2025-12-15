@echo off
chcp 65001 >nul
color 0D
title Java Applet Runner - 606
cls
echo.
echo  ╔═══════════════════════════════════════════════════════════╗
echo  ║                                                           ║
echo  ║      █████╗ ██████╗ ██████╗ ██╗     ███████╗████████╗     ║
echo  ║     ██╔══██╗██╔══██╗██╔══██╗██║     ██╔════╝╚══██╔══╝     ║
echo  ║     ███████║██████╔╝██████╔╝██║     █████╗     ██║        ║  
echo  ║     ██╔══██║██╔═══╝ ██╔═══╝ ██║     ██╔══╝     ██║        ║
echo  ║     ██║  ██║██║     ██║     ███████╗███████╗   ██║        ║
echo  ║     ╚═╝  ╚═╝╚═╝     ╚═╝     ╚══════╝╚══════╝   ╚═╝        ║
echo  ║                                                           ║
echo  ╚═══════════════════════════════════════════════════════════╝
echo.
echo  ===================================================
echo                 _____  ____   _____                      
echo                / ___/ / __ \ / ___/                      
echo               / __ \ / / / // __ \                       
echo              / /_/ // /_/ // /_/ /                       
echo              \____/ \____/ \____/  
echo.
echo  ===================================================
echo.

cd src

echo [1/2] Compiling Java files...
javac *.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    echo Check your Java code for errors.
    pause
    exit /b 1
)

echo [2/2] Starting Applet Viewer...
appletviewer index.html

cd ..
pause