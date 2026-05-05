@echo off
REM Copy Arial font from Windows system fonts to project assets
setlocal

set SOURCE=C:\Windows\Fonts\arial.ttf
set DEST=assets\fonts\

REM Create fonts directory
if not exist "%DEST%" (
    mkdir "%DEST%"
    echo Created %DEST% directory
)

REM Copy font file
if exist "%SOURCE%" (
    copy "%SOURCE%" "%DEST%" /Y
    echo Successfully copied arial.ttf to %DEST%
    echo.
    dir "%DEST%*.ttf"
) else (
    echo ERROR: arial.ttf not found in %SOURCE%
    pause
)

endlocal
