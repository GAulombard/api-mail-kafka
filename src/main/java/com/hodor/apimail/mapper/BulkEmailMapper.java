package com.hodor.apimail.mapper;

import com.hodor.apimail.dto.BulkEmailDto;
import com.hodor.apimail.dto.EmailDto;
import com.hodor.apimail.entity.BulkEmail;
import com.hodor.apimail.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BulkEmailMapper {

    BulkEmailMapper INSTANCE = Mappers.getMapper( BulkEmailMapper.class );

    BulkEmailDto toDto(BulkEmail email);
    BulkEmail toEntity(BulkEmailDto emailDto);
}
