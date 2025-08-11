#!/bin/bash

# Payment Tracker Runner Script
# This script ensures .NET is available and runs the Payment Tracker application

# Add .NET to PATH if not already there
if ! command -v dotnet &> /dev/null; then
    export PATH="$HOME/.dotnet:$PATH"
fi

# Check if .NET is available
if ! command -v dotnet &> /dev/null; then
    echo "Error: .NET is not installed or not in PATH"
    echo "Please install .NET 8.0 or later"
    exit 1
fi

# Build and run the application
echo "Building Payment Tracker..."
dotnet build

if [ $? -eq 0 ]; then
    echo "Build successful! Starting Payment Tracker..."
    echo ""
    dotnet run
else
    echo "Build failed! Please check the error messages above."
    exit 1
fi