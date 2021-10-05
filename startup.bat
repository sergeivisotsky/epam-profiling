@echo off

if "%1" == "all" (
    call docker stop booking auditor
    call docker rm -f booking auditor

    call mvn clean install -DskipTests -T1C

    call docker-compose up -d --build
)