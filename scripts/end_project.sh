#!/bin/bash

# ================================
# Shutting down the Project 🛑
# ================================

echo "Tearing down the project... See you next time! 💤"

# Step 1: Stop Docker containers for MySQL and phpMyAdmin
echo "🔹 Stopping Docker containers..."
cd ../docker && docker-compose down
if [ $? -eq 0 ]; then
  echo "✔️ Docker containers are stopped!"
else
  echo "❌ Docker containers failed to stop. Check your setup."
  exit 1
fi

# Step 2: Stop the backend Java application 🛑
echo "🔹 Stopping the backend Java application..."
cd ../backend
pkill -f 'mvn spring-boot:run'

# Step 3: Stop the frontend Angular application 🛑
echo "🔹 Stopping the frontend Angular application..."
cd ../frontend
pkill -f 'ng serve'

echo "✅ Project has been successfully shut down. Until next time! 👋"
