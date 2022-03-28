package com.zhejianglab.spring3web.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@RestController
@Tag(name = "角色控制器", description = "角色相关操作")
@RequestMapping("/role")
public class RoleController {

}

