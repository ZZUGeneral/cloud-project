package top.yhl.cloud.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yhl.cloud.security.entity.Menu;

import java.util.List;


@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    public List<Menu> selectAllMenu();
}
