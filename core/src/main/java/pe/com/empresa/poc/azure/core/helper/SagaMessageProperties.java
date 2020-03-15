package pe.com.empresa.poc.azure.core.helper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SagaMessageProperties {

    @Value("${message.source.ok}")
    private String ok;

    @Value("${message.source.not.update}")
    private String notUpdate;

    @Value("${message.source.data.not.update}")
    private String dataNotFound;

    @Value("${message.REQUEST_INVALID}")
    private String requestInvalid;

    @Value("${message.REQUEST_INVALID.short}")
    private String requestInvalidShort;

}
