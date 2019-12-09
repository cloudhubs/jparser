package edu.baylor.ecs.cloudhubs.jparser.app.response;

import edu.baylor.ecs.cloudhubs.jparser.app.context.AnalysisResultsContext;

public class BadResponse extends BaseResponse {

    public BadResponse() {
        super(ResponseCode.INTERNAL_ERROR, null, ResponseType.FAIL);
    }

    public BadResponse(String response) {
        super(ResponseCode.INTERNAL_ERROR, response, ResponseType.FAIL);
    }

    public BadResponse(String response, ResponseCode code) {
        super(code, response, ResponseType.FAIL);
    }

    @Override
    public AnalysisResultsContext get() {
        return null;
    }

}
