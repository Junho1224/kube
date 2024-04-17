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
public class ImageVO {
    private long imageId;
    String name;
    String lastModifined;
    Long lastModifiedDate;
    String type;
    String webkitRelativePath;
    Long size;
}
