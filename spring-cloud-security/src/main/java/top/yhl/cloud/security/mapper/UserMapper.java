package top.yhl.cloud.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yhl.cloud.security.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
