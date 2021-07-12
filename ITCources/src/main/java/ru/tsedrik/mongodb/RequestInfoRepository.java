package ru.tsedrik.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RequestInfoRepository extends MongoRepository<RequestInfo, UUID> {
}
