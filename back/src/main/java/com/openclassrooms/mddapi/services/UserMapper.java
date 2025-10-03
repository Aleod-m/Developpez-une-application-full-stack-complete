package com.openclassrooms.mddapi.services;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.domain.UserDTO;
import com.openclassrooms.mddapi.domain.entity.User;

@Component
@Mapper(componentModel = "spring", uses = { EntityIdMapper.class })
public interface UserMapper {

	@Mapping(target = "subscriptionIds", source = "subscriptions", qualifiedByName = "toId")
    UserDTO toDto(User entity);


	@Mapping(target = "subscriptions", source = "subscriptionIds", qualifiedByName = "toTopic")
    User toEntity(UserDTO entityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "subscriptions", source = "subscriptionIds", qualifiedByName = "toTopic")
	void updateEntityFromDTO(UserDTO entityDTO, @MappingTarget User entity);
}
