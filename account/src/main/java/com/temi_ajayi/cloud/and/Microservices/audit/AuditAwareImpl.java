package com.temi_ajayi.cloud.and.Microservices.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor(){
        return Optional.of("ACCOUNT_MS");
    }
}
