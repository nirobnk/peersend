# Running and Distributing PeerSend

## ✅ **Best Way to Run the Application**

### Option 1: Using Maven (Recommended for Development)

```bash
mvn javafx:run
```

### Option 2: Using the Launcher Script

```bash
./run.sh
```

## 📦 **JAR File Information**

The JAR file has been created at:

- **Location**: `target/PeerSend-1.0-SNAPSHOT.jar`
- **Size**: 8.0 MB (includes all JavaFX dependencies)

### Running the JAR

Due to JavaFX module system requirements, run it with:

```bash
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml \
     -jar target/PeerSend-1.0-SNAPSHOT.jar
```

**Or simply use:**

```bash
mvn javafx:run
```

## 🎁 **Creating a Distributable Package**

### For macOS - Create .app bundle:

```bash
jpackage --type dmg \
  --name PeerSend \
  --app-version 1.0 \
  --vendor "PeerSend" \
  --input target \
  --main-jar PeerSend-1.0-SNAPSHOT.jar \
  --main-class org.example.tcpfiletranffering.Main \
  --java-options '--add-modules javafx.controls,javafx.fxml'
```

### For Windows - Create .exe installer:

```bash
jpackage --type msi \
  --name PeerSend \
  --app-version 1.0 \
  --vendor "PeerSend" \
  --input target \
  --main-jar PeerSend-1.0-SNAPSHOT.jar \
  --main-class org.example.tcpfiletranffering.Main \
  --java-options '--add-modules javafx.controls,javafx.fxml'
```

### For Linux - Create .deb package:

```bash
jpackage --type deb \
  --name PeerSend \
  --app-version 1.0 \
  --vendor "PeerSend" \
  --input target \
  --main-jar PeerSend-1.0-SNAPSHOT.jar \
  --main-class org.example.tcpfiletranffering.Main \
  --java-options '--add-modules javafx.controls,javafx.fxml'
```

## 📋 **Quick Distribution Guide**

### Share the Project Folder

The simplest way to share is to give someone the entire project folder. They just need:

1. Java 21+ installed
2. Maven installed
3. Run: `mvn javafx:run`

### Share JAR + Instructions

1. Share `target/PeerSend-1.0-SNAPSHOT.jar`
2. Share this command:
   ```bash
   mvn javafx:run
   ```
   (They need Maven + Java 21)

### Create Native Installer (Best for End Users)

Use `jpackage` commands above to create a native installer/app that:

- Doesn't require Java to be installed separately
- Can be double-clicked to run
- Is platform-specific (.dmg for Mac, .exe for Windows, .deb for Linux)

## 🔧 **Requirements**

- **Java**: 21 or higher
- **Maven**: 3.6 or higher
- **JavaFX**: 21 (automatically downloaded by Maven)

## 🚀 **Testing on Same Machine**

Open two terminals and run `mvn javafx:run` in each to test sender and receiver:

- **Terminal 1**: Run as Receiver (use port 5001)
- **Terminal 2**: Run as Sender (IP: 127.0.0.1)

## 📝 **Notes**

- The JAR file includes all JavaFX dependencies (that's why it's 8MB)
- JavaFX applications require special module handling
- For the easiest distribution, use `jpackage` to create native installers
