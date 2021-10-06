@echo off

if "%1" == "all" (
    call docker stop jaeger_agent booking auditor
    call docker rm -f jaeger_agent booking auditor

    call mvn clean install -DskipTests -T1C

    call docker-compose up -d --build
)

if "%1" == "images" (
    call docker stop jaeger_agent booking auditor
    call docker rm -f jaeger_agent booking auditor
    call docker-compose up -d --build
)