package com.example.cloudnative

import org.apache.juli.logging.LogFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("configuration")
class ConfigurationProjectProperties {
    lateinit var projectName: String
    lateinit var version: String
}

