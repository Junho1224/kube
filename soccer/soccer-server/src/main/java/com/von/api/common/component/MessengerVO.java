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
public class MessengerVO {

    private String message;
    private int status;
    private String accessToken;
    private String refreshToken;
    private Long id;


}
