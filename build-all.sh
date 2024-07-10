#!/bin/bash

# Array of project directories
PROJECTS=("api-gateway" "naming-server" "order-service" "user-service")

# Function to build a single project
build_project() {
  PROJECT=$1
  echo "Building project $PROJECT..."
  cd $PROJECT || { echo "Failed to change directory to $PROJECT"; exit 1; }
  ./gradlew clean assemble
  if [ $? -ne 0 ]; then
    echo "Gradle build failed for project $PROJECT"
    exit 1
  fi
  docker build -t $PROJECT:latest .
  if [ $? -ne 0 ]; then
    echo "Docker build failed for project $PROJECT"
    exit 1
  fi
  cd ..
}

# Loop through all projects and build them
for PROJECT in "${PROJECTS[@]}"; do
  echo "Starting build for $PROJECT"
  build_project $PROJECT
  echo "Finished build for $PROJECT"
done

echo "All projects built successfully!"