package com.openclassrooms.mddapi.services;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import java.util.*;

import com.openclassrooms.mddapi.domain.TopicDTO;
import com.openclassrooms.mddapi.domain.entity.Topic;

@Component
@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicDTO toDto(Topic entity);
    Topic toEntity(TopicDTO entity);
    List<TopicDTO> toDto(List<Topic> entity);
    List<Topic> toEntity(List<TopicDTO> entity);
}
