package org.seer.ciljssa.context;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnalysisRequestContext {

    private String filepath;
    private String language;

}
