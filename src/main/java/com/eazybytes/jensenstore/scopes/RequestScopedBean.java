package com.eazybytes.jensenstore.scopes;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Slf4j
@RequestScope
@Setter
@Getter
public class RequestScopedBean {

    private String userName;

    public RequestScopedBean() {
        log.info("RequestScopedBean initialized");
    }
}
