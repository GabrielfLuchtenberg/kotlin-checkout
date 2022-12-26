package com.checkout.env

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.util.Properties

internal class HoconBasedConfig(deploymentEnv: AppOsEnvironment) : Env {
    private val config: Config

    init {
        val envConfig = ConfigFactory.load("application-${deploymentEnv.deploymentEnv}.conf")
        val commonConf = ConfigFactory.load("application-common.conf")
        val rootConfig = ConfigFactory
            .load()
            .withFallback(envConfig)
            .withFallback(commonConf)
            .resolve()
        this.config = rootConfig.getConfig("app-config")
    }
    override val database: Properties by lazy {
        val node = config.getConfig("database")
        node.toProperties()
    }

    private fun Config.toProperties() = Properties().also {
        for(e in this.entrySet()) {
            it.setProperty(e.key, this.getString(e.key))
        }
    }
}

class AppOsEnvironment {
    val deploymentEnv: String by lazy {
        System.getenv(appDeploymentEnvKey)
            ?: throw IllegalStateException("<$appDeploymentEnvKey> environment variable is missing")
    }

    private val appDeploymentEnvKey = "APP_DEPLOYMENT_ENV"
}


interface Env {
    val database : Properties;
}