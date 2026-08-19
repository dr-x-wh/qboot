package com.qboot.auth.controller;

import com.qboot.cache.redis.RedisCache;
import com.qboot.datasource.jooq.gen.tables.records.AppUserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.qboot.datasource.jooq.gen.Tables.APP_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final DSLContext dsl;

    @PostMapping("/login")
    public String login() {
        AppUserRecord appUser = dsl.insertInto(APP_USER)
                .set(APP_USER.NAME, "qcy")
                .set(APP_USER.AGE, 1)
                .returning()
                .fetchOne();
        Optional<AppUserRecord> appUserRecord = Optional.ofNullable(appUser);

        return appUserRecord.orElseThrow().formatJSON();
    }

}
