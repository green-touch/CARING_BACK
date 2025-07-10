package com.caring.manager_service.presentation.manager.vo.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class EditManagerInform {
    private final String email;
    private final String phoneNumber;
}
