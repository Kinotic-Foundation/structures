package org.kinotic.structuresserver.config;

import org.kinotic.continuum.api.security.DefaultParticipant;
import org.kinotic.continuum.api.security.ParticipantConstants;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.api.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class TemporarySecurityService implements SecurityService {


    private static final Logger log = LoggerFactory.getLogger(TemporarySecurityService.class);

    private static final String PASSWORD = "structures";
    private static final Participant participant = new DefaultParticipant("kinotic",
                                                                          "admin",
                                                                          Map.of(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY,
                                                                                 ParticipantConstants.PARTICIPANT_TYPE_USER),
                                                                          List.of("ADMIN"));

    @Override
    public CompletableFuture<Participant> authenticate(Map<String, String> authenticationInfo) {
        if(authenticationInfo.containsKey("login") && Objects.equals(authenticationInfo.get("login"), "admin")
            && authenticationInfo.containsKey("passcode") && Objects.equals(authenticationInfo.get("passcode"), PASSWORD)){
            if(log.isDebugEnabled()){
                log.debug("Successfully authenticated user: {}", participant.getId());
            }
            return CompletableFuture.completedFuture(participant);
        }else if (authenticationInfo.containsKey("authorization")){
            String authorizationHeader = authenticationInfo.get("authorization");
            // Header looks something like
            // "Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="
            String[] parts = authorizationHeader.split(" ");
            if (parts.length == 2 && "Basic".equalsIgnoreCase(parts[0])) {
                String credentials = new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
                String[] creds = credentials.split(":", 2);
                if (creds.length == 2) {
                    if (creds[0].equals("admin") && creds[1].equals(PASSWORD)) {
                        if(log.isDebugEnabled()){
                            log.debug("Successfully authenticated user: {}", participant.getId());
                        }
                        return CompletableFuture.completedFuture(participant);
                    }else{
                        if(log.isDebugEnabled()){
                            log.debug("Failed to authenticate user: {}", authenticationInfo.get("login"));
                        }
                    }
                }
            }else{
                if(log.isDebugEnabled()){
                    log.debug("Only Basic Auth is supported. Received {}", authorizationHeader);
                }
            }
        }

        return CompletableFuture.failedFuture(new AuthenticationException("username/password pair provided was not correct."));
    }
}
