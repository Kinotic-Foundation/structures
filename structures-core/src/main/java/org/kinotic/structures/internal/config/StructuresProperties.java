package org.kinotic.structures.internal.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "structures")
public class StructuresProperties {

    private boolean elasticUseSsl;
    @NotNull
    private Duration elasticConnectionTimeout;
    @NotNull
    private Duration elasticSocketTimeout;
    @NotBlank
    private String elasticUris;
    @NotBlank
    private String elasticUsername;
    @NotBlank
    private String elasticPassword;

    public boolean isElasticUseSsl() {
        return elasticUseSsl;
    }

    public void setElasticUseSsl(boolean elasticUseSsl) {
        this.elasticUseSsl = elasticUseSsl;
    }

    public Duration getElasticConnectionTimeout() {
        return elasticConnectionTimeout;
    }

    public void setElasticConnectionTimeout(Duration elasticConnectionTimeout) {
        this.elasticConnectionTimeout = elasticConnectionTimeout;
    }

    public Duration getElasticSocketTimeout() {
        return elasticSocketTimeout;
    }

    public void setElasticSocketTimeout(Duration elasticSocketTimeout) {
        this.elasticSocketTimeout = elasticSocketTimeout;
    }

    public String getElasticUris() {
        return elasticUris;
    }

    public void setElasticUris(String elasticUris) {
        this.elasticUris = elasticUris;
    }

    public String getElasticUsername() {
        return elasticUsername;
    }

    public void setElasticUsername(String elasticUsername) {
        this.elasticUsername = elasticUsername;
    }

    public String getElasticPassword() {
        return elasticPassword;
    }

    public void setElasticPassword(String elasticPassword) {
        this.elasticPassword = elasticPassword;
    }
}
