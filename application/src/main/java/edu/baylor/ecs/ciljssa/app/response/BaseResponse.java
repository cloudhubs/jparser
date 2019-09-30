package edu.baylor.ecs.ciljssa.app.response;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseResponse implements Serializable {

    protected ResponseCode responseCode;

    protected String responseMessage;

    public BaseResponse() {
    }

    public BaseResponse(ResponseCode responseCode, String responseMessage) {
        this.responseCode  = responseCode;
        this.responseMessage = responseMessage;
    }

    public BaseResponse(int responseCode, String responseMEssage) {
        this.responseCode = new ResponseCode(responseCode);
        this.responseMessage = responseMEssage;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode, String reason) {
        this.responseCode = new ResponseCode(responseCode, reason);
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


}