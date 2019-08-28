package org.seer.ciljssa.services;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;
import org.seer.ciljssa.support.DirectoryIndexer;

import java.io.File;

@Data
@NoArgsConstructor
public class AnalysisService {

    public AnalysisContext basicAnalysis(AnalysisRequestContext requestContext){
        AnalysisContext results = new AnalysisContext(requestContext);

        return results;
    }

    // TODO: Currently close to being able to read all class names from a directory. Need to wrap into a context.
    public AnalysisContext getAllClassNames(String filepath) {
        JavaParser parser = new JavaParser();
        DirectoryIndexer index = new DirectoryIndexer(DirectoryIndexer.Language.JAVA,
                (level, path, file) -> {
                    try {
                        new VoidVisitorAdapter<Object>() {
                            @Override
                            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                                super.visit(n, arg);
                            }
                        }.visit(parser.parse(file), null);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
        return new AnalysisContext();
    }
}
