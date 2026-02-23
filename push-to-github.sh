#!/bin/bash
# Script to push PeerSend to your new GitHub repository

echo "📝 Setting up Git for your new repository..."
echo ""
echo "⚠️  Make sure you've created a new repository on GitHub first!"
echo ""
read -p "Enter your GitHub repository URL (e.g., https://github.com/YOUR_USERNAME/PeerSend.git): " REPO_URL

if [ -z "$REPO_URL" ]; then
    echo "❌ No URL provided. Exiting."
    exit 1
fi

echo ""
echo "🔄 Removing old remote..."
git remote remove origin

echo "➕ Adding your repository as origin..."
git remote add origin "$REPO_URL"

echo "📦 Staging all changes..."
git add .

echo "💾 Creating commit..."
git commit -m "Enhanced PeerSend with modern UI and cross-platform fixes

- Added beautiful gradient UI with modern card-based design
- Implemented cross-platform file path handling
- Enhanced error messages with emoji indicators
- Added proper status updates during file transfer
- Created comprehensive CSS styling
- Added debugging and logging
- Fixed macOS/Windows compatibility issues"

echo "🚀 Pushing to your GitHub repository..."
git branch -M main
git push -u origin main

echo ""
echo "✅ Done! Your project is now on GitHub at: $REPO_URL"
