package Domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Clase que define un objeto de respuesta generico para todos las Apis
 * 
 * @author ricardo.ayala@pragma.com.co
 *
 * @param <T>
 */
@NoArgsConstructor
@Getter
public class ResponseDto<T> {

    private int status;
    private String responseCode;
    private String responseMessage;
    private T data;


    public ResponseDto(int status, String responseCode, String responseMessage, T data) {
        this.status = status;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public ResponseDto(int status, String responseCode, String responseMessage) {
        this(status, responseCode, responseMessage, null);
    }

    @Override
    public String toString() {
        return "ResponseDto{" + "status=" + status + ", responseCode='" + responseCode + '\'' + ", responseMessage='" + responseMessage
                + '\'' + ", data=" + data + '}';
    }
}
