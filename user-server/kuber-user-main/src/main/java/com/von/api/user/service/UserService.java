package com.von.api.user.service;

import java.util.List;
import java.util.Optional;

import com.von.api.common.component.MessengerVO;
import com.von.api.common.service.CommandService;
import com.von.api.common.service.QueryService;
import com.von.api.user.model.User;
import com.von.api.user.model.UserDTO;


public interface UserService extends CommandService<UserDTO>, QueryService<UserDTO>{
    String updatePassword(User user);
    List<UserDTO> findUsersByName(String name);
    List<UserDTO> findUsersByJob(String job);
    Optional<User> findUserByUsername(String username);
    MessengerVO login(UserDTO param);

   
    default User dtoToEntity(UserDTO dto){    //dto 를 entity로 바꾸는 것
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .job(dto.getJob())
                .build();
    }

    default UserDTO entityToDto(User ent){ //entity 를 dto로 바꾸는 것
        return UserDTO.builder()
                .id(ent.getId())
                .username(ent.getUsername())
                .password(ent.getPassword())
                .email(ent.getEmail())
                .name(ent.getName())
                .phone(ent.getPhone())
                .job(ent.getJob())
                .build();
    }

   


}
