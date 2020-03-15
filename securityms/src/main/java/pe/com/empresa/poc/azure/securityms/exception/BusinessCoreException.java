package pe.com.empresa.poc.azure.securityms.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessCoreException extends SagaCoreException
{
    private final String codeError;

    public BusinessCoreException(String codeError) {
        super(codeError);
        this.codeError = codeError;
    }

    public BusinessCoreException(String codeError, String message, String shortMessage) {
        super(codeError, message, shortMessage);
        this.codeError = codeError;
    }

    public BusinessCoreException(Throwable cause, String message, String codeError) {
        super(cause, message);
        this.codeError = codeError;
    }

    public BusinessCoreException(String message, String codeError) {
        super(message);
        this.codeError = codeError;
    }
}
