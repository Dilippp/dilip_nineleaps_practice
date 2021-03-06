# Spring Boot based application for the Product Service Java API

This project provides Spring Boot Configuration and an associated Spring Boot Starter for the Product Service Java API. The goal of this project is to make it easy to develop Product Service Java API.

## Contents

* [Overview](#overview)
* [Requirements](#requirements)
* [Running](#running)
* [Contributing](#contributing)
* [Authors](#authors)
* [License](#license)
* [Resources](#resources)

---

## Overview

This project provides Spring Boot Configuration and an associated Spring Boot Starter for the Product Service Java API. The goal of this project is to make it easy to develop Product Service Java API.

## Building the Project Yourself

This project depends on maven for building. To build the jar locally, check out the project and build from source by doing the following:

    git clone https://github.com/nineleaps-training/product-service.git
    cd product-service
    mvn package

Note: As current setup, the build requires Java 1.8.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3 or above](https://maven.apache.org)

## Running

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.nineleaps.productservice.ProductServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on the process for submitting pull/merge requests.

## Authors

See the list of [contributors](https://github.com/nineleaps-training/product-service/graphs/contributors/) who participated in this project.

## License

This project is licensed under the Apache License, Version 2.0. - See the [LICENSE](LICENSE.txt) file for details.

## ProductService Change log

Please follow the [CHANGELOG.md](CHANGELOG.md) for more details semantic versioning and logging changes

## Code of Conduct
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v1.4%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)
Please note that this project is released with a Contributor Code of Conduct. By participating in this project you agree to abide by its terms.

## Resources

For more information about Spring Boot and Starters try these resources:

- [Spring Docs](https://spring.io/projects/spring-boot)
