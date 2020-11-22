package by.ryni.share;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SecurityOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearer-key");
        operation.addSecurityItem(securityRequirement);
        return operation;
    }
}
