#!/bin/bash
# PeerSend Launcher Script

# Get the directory where this script is located
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Run the application using Maven (most reliable method)
cd "$DIR"
mvn javafx:run
