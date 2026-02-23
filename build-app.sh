#!/bin/bash
# Build Native macOS Application Package

echo "🚀 Creating native macOS application..."

# Clean and compile
mvn clean compile

# Create app image using jpackage
jpackage \
  --type app-image \
  --name PeerSend \
  --app-version 1.0 \
  --input target/classes \
  --main-jar ../../../$(find ~/.m2/repository/org/openjfx/javafx-base/21 -name "*.jar" | head -1) \
  --main-class org.example.tcpfiletranffering.Main \
  --java-options '--module-path $APPDIR/../app' \
  --java-options '--add-modules javafx.controls,javafx.fxml' \
  --dest target/dist \
  --vendor "PeerSend Team" \
  --description "Peer-to-Peer File Transfer Application"

if [ $? -eq 0 ]; then
    echo "✅ Application created successfully!"
    echo "📂 Find it in: target/dist/PeerSend.app"
    echo "🎯 You can run it by double-clicking PeerSend.app"
else
    echo "❌ Failed to create application"
fi
