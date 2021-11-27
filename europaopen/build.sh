#!/usr/bin/env bash
debug_print() {
    STARTCOLOR="\e[92m";
    ENDCOLOR="\e[0m";

    printf "\n$STARTCOLOR%b$ENDCOLOR\n" "$1";
}

debug_print "Cleaning up + installing dependencies..."
flutter clean
flutter pub get
flutter packages pub run build_runner build --delete-conflicting-outputs

debug_print "Building Android's App Bundle & APK..."
flutter build appbundle --obfuscate --split-debug-info=./debug
flutter build apk --obfuscate --split-debug-info=./debug
