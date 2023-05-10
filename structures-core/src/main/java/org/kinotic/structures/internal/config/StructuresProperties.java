package org.kinotic.structures.internal.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.internal.api.services.util.StructuresHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "structures")
public class StructuresProperties {

    @NotNull
    private String indexPrefix = "struct_";
    @NotNull
    private Duration elasticConnectionTimeout = Duration.ofMinutes(1);
    @NotNull
    private Duration elasticSocketTimeout = Duration.ofMinutes(1);
    @NotNull
    private List<ElasticConnectionInfo> elasticConnections = List.of(new ElasticConnectionInfo("localhost", 9200));
    @NotBlank
    private String elasticUsername = "";
    @NotBlank
    private String elasticPassword = "";

    private OpenApiSecurityType openApiSecurityType = OpenApiSecurityType.NONE;

    @PostConstruct
    public void validate(){
        // this will validate we do not contain invalid characters
        // FIXME: should we limit the number of chars as well?
        StructuresHelper.indexNameValidation(indexPrefix);
    }

    public boolean hasElasticUsernameAndPassword(){
        return elasticUsername != null && !elasticUsername.isBlank() &&  elasticPassword != null && !elasticPassword.isBlank();
    }
}
