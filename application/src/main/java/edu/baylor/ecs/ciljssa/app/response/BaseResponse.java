package edu.baylor.ecs.ciljssa.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.app.context.AnalysisResultsContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
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