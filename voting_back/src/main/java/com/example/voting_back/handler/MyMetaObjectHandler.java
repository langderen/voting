package com.example.voting_back.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "userId", Long.class, null);
        this.strictInsertFill(metaObject, "commentId", Long.class, null);
        this.strictInsertFill(metaObject, "optionId", Long.class, null);
        this.strictInsertFill(metaObject, "pollId", Long.class, null);
        this.strictInsertFill(metaObject, "tagId", Long.class, null);
        this.strictInsertFill(metaObject, "vote_id", Long.class, null);

        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "votedAt", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //log.info("开始更新填充...");
        //this.strictInsertFill(metaObject, "updateUserId", Long.class, 123456L)
        this.strictUpdateFill(metaObject, "lastLogin", LocalDateTime.class, LocalDateTime.now());
    }
}
