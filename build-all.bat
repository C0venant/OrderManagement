@echo off

set PROJECTS=(api-gateway naming-server order-service user-service)

echo Cleaning, assembling, and building Docker images for all projects...

for %%P in %PROJECTS% do (
    echo Building project %%P...
    cd %%P
    call gradlew clean assemble
    if %errorlevel% neq 0 (
        echo Gradle build failed for project %%P
        exit /b %errorlevel%
    )
    docker build -t %%P:latest .
    if %errorlevel% neq 0 (
        echo Docker build failed for project %%P
        exit /b %errorlevel%
    )
    cd ..
)

echo All projects built successfully!