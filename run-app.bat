
@echo off
set "PATH=%WINDIR%\System32\WindowsPowerShell\v1.0;%WINDIR%\System32;%PATH%"
set "MAVEN_USER_HOME=E:\java\.maven"
set "MAVEN_OPTS=-Dmaven.repo.local=E:\java\.m2\repository"

call mvnw.cmd spring-boot:run

pause
