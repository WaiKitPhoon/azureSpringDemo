package com.springazure.demo.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DatasourceConfig {

    @Value("\${connectionString}")
    private var connectionString = ""

    @Value("\${dockerConnection}")
    private var dockerConnection = ""

    @Value("\${dscredentials}")
    private var dataSourceCredentials = ""

    @Value("\${driverClassName}")
    private var driverClassName = ""

    @Value("\${spring.profiles.active}")
    private val activeProfile = ""


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun getDataSource(): DataSource? {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.url(retrieveJDBCfromProfile())
        dataSourceBuilder.username(dataSourceCredentials)
        dataSourceBuilder.password(dataSourceCredentials)
        dataSourceBuilder.driverClassName(driverClassName)
        getSecrets(retrieveJDBCfromProfile(), dataSourceCredentials, driverClassName)
        return dataSourceBuilder.build()
    }

    fun getSecrets(connection: String, credential: String, driverClassName: String) {
        println(connection)
        println(credential)
        println(driverClassName)
    }

    fun retrieveJDBCfromProfile() : String {
        return when (activeProfile) {
            "dev" -> connectionString
            "docker"-> dockerConnection
            else -> connectionString
        }
    }


}