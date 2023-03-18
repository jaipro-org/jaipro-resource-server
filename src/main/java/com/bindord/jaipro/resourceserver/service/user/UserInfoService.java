package com.bindord.jaipro.resourceserver.service.user;

import com.bindord.jaipro.resourceserver.domain.user.UserInfo;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserInfoDto;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserInfoUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;

import java.util.UUID;

public interface UserInfoService extends BaseService<UserInfo, UUID, UserInfoDto, UserInfoUpdateDto> {

}
