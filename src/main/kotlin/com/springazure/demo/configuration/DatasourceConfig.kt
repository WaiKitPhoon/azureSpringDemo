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

    @Value("\${dscredentials}")
    private var dataSourceCredentials = ""

    @Value("\${driverClassName}")
    private var driverClassName = ""


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun getDataSource(): DataSource? {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.url(connectionString)
        dataSourceBuilder.username(dataSourceCredentials)
        dataSourceBuilder.password(dataSourceCredentials)
        dataSourceBuilder.driverClassName(driverClassName)
        getSecrets(connectionString, dataSourceCredentials, driverClassName)
        return dataSourceBuilder.build()
    }

    fun getSecrets(connection: String, credential: String, driverClassName : String) {
        println(connection)
        println(credential)
        println(driverClassName)
    }

}