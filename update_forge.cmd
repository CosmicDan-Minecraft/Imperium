@echo off
echo Ensure you have modified build.gradle with the new versions string...
pause
call gradlew.bat setupDecompWorkspace
call gradlew.bat eclipse
echo.
echo.
echo.
echo Done!
pause
