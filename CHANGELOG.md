# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## 1.4.0 - 2017-11-30
### Added
- new feature that help to specify the output format of the matching results

### Updated
- the sdk to support set of output format
- ImageMatching Task

## 1.4.0 - 2017-11-30
### Added
- object proposal feature

### Update
- gradle to 3.0.0
- update used libs in SDK
- introduced compile only in the SDK build.gradle

### Removed
- login call from the SDK
- AuthManger
- AccessToken 
- AuthTask
- Deprecated calls like login and enableCrushReporting 
- onMatched(JsonObject) call from IMatchCallback

### Modified 
- init SDK with Client Id
- version of gson and okhttp library  



## 1.3.0 - 2017-11-23
### Added
- Some deprecated methods
- Hard coded nyris api url

### Removed
- Firebase Crash
- Firebase Remote config

### Changed 
- Helpers class no more static instance
- HttpRequestHelper class no more static instance

## 1.2.2 - 2017-11-16
### Changed 
- Migrated the old endpoints o the newest one

## 1.2.1
### Changed 
- Accept header for http matching request

## 1.2.0
### Added
- Search only similar feature

###Changed
- ImageMatchingTask to support search only similar offers and to create the request inside the task
- Changed HttpRequestHelper to receive request as param

## 1.1.1 - 2017-09-13
### Added
- Checked if token creation date is null to avoid null exception and force the sdk to get the new 
token

## 1.1.1 - 2017-09-13
### Added
- Token Expiration handling

## 1.1.0 - 2017-09-12
### Changed
- Nyris Live endpoints format

### Removed 
- SDK minifying 

## 1.0.0 - 2017-09-08
### Added
- This CHANGELOG file to hopefully serve as an evolving example of a standardized open source 
project CHANGELOG.
- README now contains how to use nyris IMX SDK
- A License file
- SDK library for Image matching
- A demo app that show how you can use the SDK