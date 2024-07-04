package com.example.bookmaru.domain.api.repository;

import com.example.bookmaru.domain.api.model.EmailCodeDto;
import org.springframework.data.repository.CrudRepository;

public interface EmailCodeRedisRepository extends CrudRepository<EmailCodeDto,String > {

}
