# Code-sign Java for macOS

[![Build](https://github.com/drrename/codesign-java-for-mac/actions/workflows/build.yml/badge.svg)](https://github.com/drrename/codesign-java-for-mac/actions/workflows/build.yml) [![License](https://img.shields.io/github/license/drrename/codesign-java-for-mac.svg)](https://github.com/drrename/codesign-java-for-mac/blob/master/LICENSE)

## Library

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.drrename.codesignjava/codesignjava/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.drrename.codesignjava/codesignjava)

## Maven Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.drrename.codesignjava/codesignjava-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.drrename.codesignjava/codesignjava-maven-plugin)

Tools for code-signing and notarization of Java applications for Apple macOS

## HowTo Code-signing and Notarization for macOS

### Tell Apple about yourself and your App

1. Register as a developer at [developer.apple.com](https://developer.apple.com/).

2. Go to [developer.apple.com/account/resources/certificates/list](https://developer.apple.com/account/resources/certificates/list) and create a new Certificate:

   ![localImage](images/Apple-Developer-Certificate.png)

3. Go to [appstoreconnect.apple.com/access/api](https://appstoreconnect.apple.com/access/api)
   and create a developer API key.

   ![localImage](images/Apple-Developer-App-Store-Connect-API-Keys.png)

   You will need the "Issuer ID" and the "KEY ID" later for the notarization.

   **Important**: Download the API key, that is only possible once right after creation of the key! You will need it later.

   create folder `.private_keys` in home and move `*.p8` file there: `mkdir -p ~/private_keys; cp *.p8 ~/.private_keys`

4. Go to [developer.apple.com/account/resources/certificates/list](https://developer.apple.com/account/resources/certificates/list)
   and register a new identifier for the app.

   ![localImage](images/Apple-Developer-Certificates-Identifiers-Profies.png)

5. Go to [appstoreconnect.apple.com/apps](https://appstoreconnect.apple.com/apps)
   and register a new App with the created identifier.

   ![localImage](images/Apple-Developer-new-App.png)

### Codesign your App

#### On `codesign --deep`

On [developer.apple.com/forums/thread/128166](https://developer.apple.com/forums/thread/128166) they write:

> Do not use the --deep argument. This feature is helpful in some specific circumstances but it will cause problems when signing a complex program.

See here [developer.apple.com/forums/thread/129980](https://developer.apple.com/forums/thread/129980) for more info.

#### GitHub Actions

1. Download your certificate.

2. Convert to base64 string and copy it to your clipboard: `base64 -i Developer\ ID\ Application\ John\ Doer\ \(12345ABCDE\).p12 | pbcopy`

3. Create a repository variable and paste the base64 string:

   ![localImage](/images/GitHub_MAC_DEVELOPER_CERTIFICATE.png)

4. Create another variable and paste your password:

   ![localImage](/images/GitHub_MAC_DEVELOPER_CERTIFICATE_PASSWORD.png)

### Notarize your App

#### Submit Notarization Request

`xcrun altool --notarize-app --primary-bundle-id "com.drkodi" --apiKey "ABCDE12345" --apiIssuer "3a8a0000-5288-41dd-8527-b0000000028a" -t osx -f DrKodi.app.zip --output-format json`

**Note**: trying to upload an .app file will fail. Instead, zip it first and upload the zip file.

#### Query for Results

`xcrun altool --notarization-info 'cb00ab00-b46b-424a-b0dd-d4f7f9111147' --primary-bundle-id "com.drkodi" --apiKey "ABCDE12345" --apiIssuer "3a8a0000-5288-41dd-8527-b0000000028a" --output-format json`

#### GitHub Actions

## Shell script

## Java API

## Sources

+ [github.com/Apple-Actions/upload-testflight-build/issues/27](https://github.com/Apple-Actions/upload-testflight-build/issues/27)
+ [docs.github.com/en/actions/deployment/deploying-xcode-applications/installing-an-apple-certificate-on-macos-runners-for-xcode-development](https://docs.github.com/en/actions/deployment/deploying-xcode-applications/installing-an-apple-certificate-on-macos-runners-for-xcode-development)
