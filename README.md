# camunda-decision

[![example](https://img.shields.io/badge/lifecycle-EXAMPLE-blue.svg)](https://github.com/holisticon#open-source-lifecycle)
[![incubating](https://img.shields.io/badge/lifecycle-INCUBATING-orange.svg)](https://github.com/holisticon#open-source-lifecycle)
[![Build Status](https://github.com/holunda-io/camunda-decision/workflows/Development%20branches/badge.svg)](https://github.com/holunda-io/camunda-decision/actions)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e56194c76382443ea1cfa69bc1a8d7e3)](https://app.codacy.com/gh/holunda-io/camunda-decision?utm_source=github.com&utm_medium=referral&utm_content=holunda-io/camunda-decision&utm_campaign=Badge_Grade_Dashboard)
[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-RED.svg)](https://holisticon.de/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.holunda.decision/camunda-decision-model-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.holunda.decision/camunda-decision-model-api)

Extension for easier, type safe interaction with camunda dmn.

## Usage

```xml
<dependency>
    <groupId>io.holunda.decision</groupId>
    <artifactId>camunda-decision-model-api</artifactId>
    <version>0.0.1</version>
</dependency>
```


## DmnWriter

Prints [ascii table](http://www.vandermeer.de/projects/skb/java/asciitable/features.html) like:

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│                        DMN Example ('example') - v666                        │
├───────────────────┬───────────────────┬───────────────────┬──────────────────┤
│     Foo Value     │     Bar Value     │    VIP Status     │                  │
│  'foo' (INTEGER)  │  'bar' (BOOLEAN)  │ 'status' (STRING) │                  │
├───────────────────┴───────────────────┼───────────────────┼──────────────────┤
│               - INPUT -               │     - OUTPUT -    │ - DESCRIPTION -  │
├───────────────────┬───────────────────┼───────────────────┼──────────────────┤
│       > 10        │       true        │     "SILVER"      │  this is great   │
├───────────────────┼───────────────────┼───────────────────┼──────────────────┤
│       < 10        │         -         │     "BRONZE"      │   not so good    │
├───────────────────┼───────────────────┼───────────────────┼──────────────────┤
│         -         │       false       │      "GOLD"       │     amazing!     │
├───────────────────┼───────────────────┼───────────────────┼──────────────────┤
│         -         │         -         │     "DEFAULT"     │        -         │
└───────────────────┴───────────────────┴───────────────────┴──────────────────┘
```

## FEEL

* <https://camunda.github.io/feel-scala/docs/reference/language-guide/feel-data-types/>
* <https://docs.camunda.org/manual/7.15/reference/dmn/>
* <https://consulting.camunda.com/dmn-simulator/>
