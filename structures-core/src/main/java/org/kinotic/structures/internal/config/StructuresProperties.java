package org.kinotic.structures.internal.config;


import org.kinotic.structures.internal.api.services.util.StructureHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "structures")
public class StructuresProperties {

    @NotNull
    private String indexPrefix = "struct_";
    @NotNull
    private Boolean elasticUseSsl = true;
    @NotNull
    private Duration elasticConnectionTimeout = Duration.ofMinutes(1);
    @NotNull
    private Duration elasticSocketTimeout = Duration.ofMinutes(1);
    @NotBlank
    private String elasticUris = "localhost:9200";
    @NotBlank
    private String elasticUsername = "";
    @NotBlank
    private String elasticPassword = "";

    private OpenApiSecurityType openApiSecurityType = OpenApiSecurityType.NONE;

    @PostConstruct
    public void validate(){
        // this will validate we do not contain invalid characters
        // FIXME: should we limit the number of chars as well?
        StructureHelper.indexNameValidation(indexPrefix);
    }

    public String getIndexPrefix() {
        return indexPrefix;
    }

    public void setIndexPrefix(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }

    public Boolean isElasticUseSsl() {
        return elasticUseSsl;
    }

    public void setElasticUseSsl(Boolean elasticUseSsl) {
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

    public OpenApiSecurityType getOpenApiSecurityType() {
        return openApiSecurityType;
    }

    public void setOpenApiSecurityType(OpenApiSecurityType openApiSecurityType) {
        this.openApiSecurityType = openApiSecurityType;
    }
}
