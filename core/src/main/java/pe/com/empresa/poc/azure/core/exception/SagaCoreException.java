package pe.com.empresa.poc.azure.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pe.com.empresa.poc.azure.core.helper.SerializableCoreVersion;

@SuppressWarnings("all")
public class SagaCoreException extends RuntimeException {
    private static final long serialVersionUID = SerializableCoreVersion.SERIAL_VERSION_UID;
    @Getter
    private String codeError;
    @Getter
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    @Getter
    private String message;
    @Getter
    private String shortMessage;

    public SagaCoreException(String codeError){
        this.codeError = codeError;
    }

    public SagaCoreException(String codeError, String message){
        this.codeError = codeError;
        this.message = message;
    }
    public SagaCoreException(String codeError, HttpStatus status){
        this.codeError = codeError;
        this.status = status;
    }
    public SagaCoreException(String codeError, HttpStatus status, String message, String shortMessage){
        this.codeError = codeError;
        this.status = status;
        this.message = message;
        this.shortMessage = shortMessage;
    }
    
    public SagaCoreException(Throwable cause, String codeError, HttpStatus status) {
      this(cause, codeError);
      this.status = status;
    }

    public SagaCoreException(Throwable cause, String codeError){
        super(cause);
        this.codeError = codeError;
    }

    public SagaCoreException(Throwable cause){
        super(cause);
        if ( cause instanceof SagaCoreException) {
            this.codeError = ((SagaCoreException) cause).getCodeError();
        }
    }
    
    public SagaCoreException(Throwable cause, HttpStatus status) {
      this(cause);
      this.status = status;
    }

    public SagaCoreException(String codeError, String message, String shortMessage ){
        this.codeError = codeError;
        this.message = message;
        this.shortMessage = shortMessage;
    }

    public SagaCoreException(){
    }
}
