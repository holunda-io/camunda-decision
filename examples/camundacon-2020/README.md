# Demo

## links

* cockpit: <http://localhost:10110/> (admin/admin)
* swagger: <http://localhost:10110/swagger-ui.html>

## shortcuts:

* 0: README
* 1: http runner
* 2: main method
* 3: CombinedLegalAndProductGenerator

CMD-1 - project

CMD-4 - RUN

CMD-8 - services

## Plan

* show project layout
  
  * don't be afraid: it's kotlin
  * ordinary spring boot app using camunda starter
  * database postgres from docker
  * dmn repo outside of project

* run app is running

* show cockpit: no auto deployment, app is fresh and empty

* goto main (2), implement dummy
  
  * print out ascii
  * print xml

* goto Generator (3)
  
  * show how we can load reference table isAdult
  * deploy

* go to cockpit
  
  * show decision requirement graph
* evaluate decision
* back to cockpit-history

## Data

```kotlin
CustomerEntity(id = "1", name = "Peter", age = 19, sex = "male", country = "USA", state = "Alabama")
CustomerEntity(id = "2", name = "Paul", age = 17, sex = "male", country = "Pakistan")
CustomerEntity(id = "3", name = "Mary", age = 17, sex = "female", country = "Pakistan")
```
