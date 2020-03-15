# camunda-decision

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e56194c76382443ea1cfa69bc1a8d7e3)](https://app.codacy.com/gh/holunda-io/camunda-decision?utm_source=github.com&utm_medium=referral&utm_content=holunda-io/camunda-decision&utm_campaign=Badge_Grade_Dashboard)

Extension for easier, type safe interaction with camunda dmn.

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
