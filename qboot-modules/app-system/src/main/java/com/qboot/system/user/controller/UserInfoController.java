package com.qboot.system.user.controller;

import com.qboot.common.enums.ErrorCode;
import com.qboot.common.error.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class UserInfoController {

    @GetMapping
    public Map<String, Object> get() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 15);
        return map;
    }

    @GetMapping("/{num}")
    public Void get(@PathVariable int num) {
        if (num != 0) throw new BusinessException(ErrorCode.INVALID_OPERATION, "编号不能为0");
        return null;
    }
}
