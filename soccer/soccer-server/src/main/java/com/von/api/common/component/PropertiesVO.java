package com.von.api.common.component;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertiesVO {
    private Boolean enabled;
    private String location;
    private String maxFileSize;
    private String maxRequestSize;
    private String fileSizeThreshold;

    
}
