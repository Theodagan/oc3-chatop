#!/bin/bash

# ================================
# Starting the Project 🚀
# ================================

echo "Initializing the project... Let's go! 🌟"

# Step 1: Launch Docker containers for MySQL and phpMyAdmin
echo "🔹 Starting Docker containers..."
cd ../docker && docker-compose up -d
if [ $? -eq 0 ]; then
  echo "✔️ Docker containers are up and running!"
else
  echo "❌ Docker containers failed to start. Check the docker-compose.yml."
  exit 1
fi

# Step 2: Launch the backend Java application 🚀
echo "🔹 Starting the backend Java application..."
cd ../backend
mvn spring-boot:run &

# Step 3: Launch the frontend Angular application 🌐
echo "🔹 Starting the frontend Angular application..."
cd ../frontend
#nvm use 16 # Ensures the correct Node version
ng serve --open &

echo "✅ Project is ready. Now, you can access the app!"
