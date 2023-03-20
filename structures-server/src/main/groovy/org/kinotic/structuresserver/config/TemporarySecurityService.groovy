package org.kinotic.structuresserver.config

import org.kinotic.continuum.core.api.event.EventConstants
import org.kinotic.continuum.core.api.security.MetadataConstants
import org.kinotic.continuum.core.api.security.Participant
import org.kinotic.continuum.core.api.security.Permissions
import org.kinotic.continuum.core.api.security.SecurityService
import org.kinotic.continuum.internal.core.api.security.DefaultParticipant
import reactor.core.publisher.Mono

class TemporarySecurityService implements SecurityService {

    private static final String PASSWORD = "structures"

    private static final Map<String, Participant> participantMap = Map.of("testuser@kinotic.org",
            new DefaultParticipant("testuser@kinotic.org",
                    Map.ofEntries(MetadataConstants.USER_TYPE),
                    new Permissions(List.of(EventConstants.SERVICE_DESTINATION_SCHEME + "://*.**",
                            EventConstants.STREAM_DESTINATION_SCHEME + "://*.**"),
                            List.of(EventConstants.SERVICE_DESTINATION_SCHEME + "://*.**",
                                    EventConstants.STREAM_DESTINATION_SCHEME + "://*.**"))))


    @Override
    Mono<Participant> authenticate(String accessKey, String secretToken) {
        if(participantMap.containsKey(accessKey) && secretToken.trim() == PASSWORD){
            return Mono.just(participantMap.get(accessKey))
        }else{
            return Mono.error(new IllegalArgumentException("Participant cannot be authenticated with information provided"))
        }
    }

    @Override
    Mono<Participant> findParticipant(String participantIdentity) {
        if(participantMap.containsKey(participantIdentity)){
            return Mono.just(participantMap.get(participantIdentity))
        }else{
            return Mono.error(new IllegalArgumentException("Participant cannot be found for the information provided"))
        }
    }

}