[![Build Status](https://travis-ci.org/SBI-/minigit.svg?branch=master)](https://travis-ci.org/SBI-/minigit)

# minigit
Extremely small and light weight java git service library.

# Motivation
This library is intended for use within [IBM RTC server side plugin](https://github.com/jazz-community/jazz-plugin-maven-archetype) projects. All acceptably good github and gitlab libraries pull in dependencies that are incompatible with many libraries that RTC uses for it's own implementation on the server. Builds and deployments using such libraries will always fail. 

The aim of this project is to build a library that can be integrated with RTC plugins, and yet provide enough functionality to enable the construction of a [git integration](https://github.com/jazz-community/rtc-git-connector) for RTC. An example of a service project that depends on this library is the [RTC Git Connector Service](https://github.com/jazz-community/rtc-git-connector-service).

Current functionality is extremely limited, and only features that are required by upstream services are implemented. This allows keeping the scope of this project as small as possible while providing enough functionality to stream-line service development.

# Usage

## Maven
To use minigit as a dependency with maven: 
1. clone this repository with `git clone git@github.com:jazz-community/jazz-plugin-maven-archetype.git` or `git clone https://github.com/jazz-community/jazz-plugin-maven-archetype.git`.
2. Install the library to your maven repository using `mvn install` or the provided maven wrapper `./mvnw install`
3. Use the library in any project you like by adding it as a dependency to your pom file:
      ```
      <dependency>
        <groupId>ch.sbi</groupId>
        <artifactId>minigit</artifactId>
        <version>LATEST_VERSION</version>
      </dependency>
      ```

