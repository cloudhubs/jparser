package edu.baylor.ecs.cloudhubs.jparser.app.context;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestContext {

    private String filepath;
    private String language; // Useless

}
