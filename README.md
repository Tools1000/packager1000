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
   
   create folder `.private_keys` in home and move `*.p8` file there: `mkdir -p ~/private_keys; cp *.p8 ~/.private_keys`

### Codesign your App

#### GitHub Actions

1. Download your certificate.

2. Convert to base64 string and copy it to your clipboard: `base64 -i Developer\ ID\ Application\ John\ Doer\ \(12345ABCDE\).p12 | pbcopy`

3. Create a repository variable and paste the base64 string:

   ![localImage](/images/GitHub_MAC_DEVELOPER_CERTIFICATE.png)

4. Create another variable and paste your password:

   ![localImage](/images/GitHub_MAC_DEVELOPER_CERTIFICATE_PASSWORD.png)

### Notarize your App


## Shell script

## Java API

## Sources

+ [github.com/Apple-Actions/upload-testflight-build/issues/27](https://github.com/Apple-Actions/upload-testflight-build/issues/27)
