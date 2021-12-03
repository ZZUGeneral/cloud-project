package top.yhl.cloud.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yhl.cloud.oauth.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
