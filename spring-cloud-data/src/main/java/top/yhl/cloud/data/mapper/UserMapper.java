package top.yhl.cloud.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yhl.cloud.data.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
