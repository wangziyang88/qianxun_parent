package com.qianxun.project.system.mapper;

import java.util.List;

import com.qianxun.project.system.domain.vo.UserTeam;

/**
 * 用户团队等操作
 * 
 */
public interface UserTeamMapper
{
    
    List<UserTeam> selectUserTeamList();
    
}
