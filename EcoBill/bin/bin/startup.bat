@echo off
set TMP_CLASSPATH=%CLASSPATH%

for %%i in (%0) do cd /d %%~dpi\..


set CLASSPATH=%CLASSPATH%;.\classes\
rem Add all jars....
for %%i in (".\lib\*.jar") do call ".\bin\cpappend.bat" %%i

set ECOBILL_CLASSPATH=%CLASSPATH%
set CLASSPATH=%TMP_CLASSPATH%

java -cp "%ECOBILL_CLASSPATH%" -Xms24m -Xmx512m ecobill.Start %*

