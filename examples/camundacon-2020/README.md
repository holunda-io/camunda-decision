# Demo

## links

* cockpit: <http://localhost:10110/> (admin/admin)
* swagger: <http://localhost:10110/swagger-ui.html>

## Plan

* show project layout
  * don't be afraid: it's kotlin
  * ordinary spring boot app using camunda starter
  * database postgres from docker
  * dmn repo outside of project
  
* run app

* show cockpit: no auto deployment, app is fresh and empty

* deploy the single `legal_age.dmn`

## Data

```kotlin
CustomerEntity(id = "1", name = "Peter", age = 19, sex = "male", country = "USA", state = "Alabama")
CustomerEntity(id = "2", name = "Paul", age = 17, sex = "male", country = "Pakistan")
CustomerEntity(id = "3", name = "Mary", age = 17, sex = "female", country = "Pakistan")
```
