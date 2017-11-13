package cn.gson.crm.model.dao;

import cn.gson.crm.model.domain.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceDao extends PagingAndSortingRepository<Resource, Long> {

    /**
     * 默认权重排序
     */
    Sort WEIGHT_SORT = new Sort(Sort.Direction.DESC, "weight");

    //管理列表树形数据
    List<Resource> findByParentIsNull();

    List<Resource> findByParent(Resource resource, Sort sort);


    //查找可用的资源
    List<Resource> findByStatusAndParentIsNull(Boolean status, Sort sort);

    List<Resource> findByStatusAndParent(Boolean status, Resource resource, Sort sort);

    List<Resource> findByStatus(Boolean status, Sort sort);
}
