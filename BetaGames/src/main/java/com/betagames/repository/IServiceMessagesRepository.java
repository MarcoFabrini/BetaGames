package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.MessageID;
import com.betagames.model.ServiceMessages;

@Repository
public interface IServiceMessagesRepository extends JpaRepository<ServiceMessages, MessageID> {

}
