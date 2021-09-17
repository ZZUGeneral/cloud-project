package top.yhl.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yhl.cloud.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
