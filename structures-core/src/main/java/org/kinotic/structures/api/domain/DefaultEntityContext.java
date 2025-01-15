package org.kinotic.structures.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.api.security.Participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 6/8/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DefaultEntityContext implements EntityContext {

    private Participant participant;

    private List<String> includedFieldsFilter = null;

    private Map<String, Object> data;

    public DefaultEntityContext(Participant participant,
                                List<String> includedFieldsFilter) {
        this.participant = participant;
        this.includedFieldsFilter = includedFieldsFilter;
    }

    public DefaultEntityContext(Participant participant) {
        this.participant = participant;
    }

    @Override
    public boolean hasIncludedFieldsFilter() {
        return includedFieldsFilter != null;
    }

    @Override
    public EntityContext put(String key, Object obj) {
        getData().put(key, obj);
        return this;
    }

    @Override
    public EntityContext putAll(Map<String, Object> value) {
        getData().putAll(value);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Object obj = getData().get(key);
        return (T)obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T remove(String key) {
        Object obj = getData().remove(key);
        return (T)obj;
    }

    @Override
    public Map<String, Object> data() {
        return getData();
    }

    private Map<String, Object> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }

}
