package com.t1.sys.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t1.sys.base.entity.UserRole;
import com.t1.common.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Insert("insert into sys_user_role(user_id, role_id) values(#{userId}, #{roleId})")
    int saveUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    @Select("<script>select r.*,ru.user_id from sys_user_role ru inner join sys_role r on r.id = ru.role_id where ru.user_id IN " +
            " <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</script>")
    List<Role> findRolesByUserIds(List<Long> userIds);
}
