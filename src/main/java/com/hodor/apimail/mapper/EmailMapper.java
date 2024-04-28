package com.hodor.apimail.mapper;

import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmailMapper {

    EmailMapper INSTANCE = Mappers.getMapper( EmailMapper.class );

    EmailDto toDto(Email email);
    Email toEntity(EmailDto emailDto);
}
