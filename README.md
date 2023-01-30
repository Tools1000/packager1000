# Code-sign Java for macOS

[![Build](https://github.com/drrename/codesign-java-for-mac/actions/workflows/build.yml/badge.svg)](https://github.com/drrename/codesign-java-for-mac/actions/workflows/build.yml) [![Latest Release](https://img.shields.io/github/release/drrename/codesign-java-for-mac.svg)](https://github.com/DrRename/codesign-java-for-mac/releases/latest) [![Release Date](https://img.shields.io/github/release-date/drrename/codesign-java-for-mac?color=blue)](https://github.com/DrRename/codesign-java-for-mac/releases/latest) [![License](https://img.shields.io/github/license/drrename/codesign-java-for-mac.svg)](https://github.com/drrename/codesign-java-for-mac/blob/master/LICENSE) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.drrename.codesign-java-for-mac/codesign-java-for-mac/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.drrename/codesign-java-for-mac)

Tools for code-signing and notarization of Java applications for Apple macOS

## General HowTo Code-signing and Notarization for macOS

### Tell Apple about your App

1. Register as a developer at [developer.apple.com](https://developer.apple.com/).

2. Go to [developer.apple.com/account/resources/certificates/list](https://developer.apple.com/account/resources/certificates/list)
   and register a new identifier for the app.

   ![localImage](/images/Apple-Developer-Certificates-Identifiers-Profies.png)

3. Go to [appstoreconnect.apple.com/apps](https://appstoreconnect.apple.com/apps)
   and register a new App with the created identifier.

   ![localImage](/images/Apple-Developer-new-App.png)

4. Go to [appstoreconnect.apple.com/access/api](https://appstoreconnect.apple.com/access/api)
   and create a developer API key.

   ![localImage](/images/Apple-Developer-App-Store-Connect-API-Keys.png)

   You will need the "Issuer ID" and the "KEY ID" later for the notarization.
   
   **Important**: Download the API key, that is only possible once right after creation of the key! You will need it later.

### Codesign your App

### Notarize your App

## Shell script

## Java API
