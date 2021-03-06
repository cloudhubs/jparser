package edu.baylor.ecs.cloudhubs.jparser.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.cloudhubs.jparser.app.context.AnalysisResultsContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class BaseResponse implements Serializable {

    @JsonProperty(value = "response")
    private ResponseCode responseCode;

    @JsonProperty(value = "detailed_response")
    private String responseMessage;

    @JsonProperty(value = "message_type")
    private ResponseType responseType;

    public BaseResponse() {
        this.responseCode = ResponseCode.OK;
    }

    public abstract AnalysisResultsContext get();

}