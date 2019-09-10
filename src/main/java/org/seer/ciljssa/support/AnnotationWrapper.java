package org.seer.ciljssa.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.metamodel.PropertyMetaModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AnnotationWrapper {

    private String annotationMetaModel;
    private String metaModelFieldName;
    @JsonProperty(value = "name")
    private String asString; // Keep this
    private String packageName;

    public void setAsString(String inp) {
        this.asString = (inp.startsWith("@") ? "" : "@") + inp;
    }

}
