package kr.co.rur

import kr.co.rur.storage.db.core.config.JpaConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(JpaConfig::class)
class RurApplication

fun main(args: Array<String>) {
	runApplication<RurApplication>(*args)
}
