@file:Suppress("PackageDirectoryMismatch", "unused")

package de.holisticon.ranked.h2

import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.RestController

fun main(args: Array<String>) {
  runApplication<H2Application>(*args)
}

@SpringBootApplication
@RestController // should be data-rest, but see https://github.com/springfox/springfox/issues/1957
class H2Application(
  @Value("\${ranked.h2.port:9092}") val port: String
) {

  @Bean(initMethod = "start", destroyMethod = "stop")
  fun h2Server(): Server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", port)


}
