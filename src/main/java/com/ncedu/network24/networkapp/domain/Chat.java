package com.ncedu.network24.networkapp.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chats", uniqueConstraints = @UniqueConstraint(
        columnNames = {"firstUserId", "secondUserId"}))
@JsonAutoDetect
public class Chat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "chat_id")
    private Long chatId;
    // @Column(name = "first_user_id")
    private Long firstUserId;
    // @Column(name = "second_user_id")
    private Long secondUserId;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }
}
