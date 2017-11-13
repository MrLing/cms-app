package cn.gson.crm.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.gson.crm.model.domain.Member;

@Mapper
public interface MemberMapper {

	public List<Member> findAll();
}
