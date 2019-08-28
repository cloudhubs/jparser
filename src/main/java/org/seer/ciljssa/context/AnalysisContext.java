package org.seer.ciljssa.context;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class AnalysisContext {

    private AnalysisRequestContext requestContext;
    private String language;

    private File sourceFile;
    private String stringifiedFile;

    private String[] classNames;

    public AnalysisContext(AnalysisRequestContext requestContext){
        this.requestContext = requestContext;
        //TODO: Nab the file name and language via Javaparser.
    }

}
