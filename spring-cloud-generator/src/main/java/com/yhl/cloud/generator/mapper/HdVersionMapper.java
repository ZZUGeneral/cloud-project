package com.yhl.cloud.generator.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yhl.cloud.generator.model.HdVersion;
import com.yhl.cloud.generator.model.HdVersionExample;

@Mapper
public interface HdVersionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    long countByExample(HdVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int deleteByExample(HdVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int insert(HdVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int insertSelective(HdVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    List<HdVersion> selectByExample(HdVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    HdVersion selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") HdVersion record, @Param("example") HdVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") HdVersion record, @Param("example") HdVersionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(HdVersion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hd_version
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(HdVersion record);
}