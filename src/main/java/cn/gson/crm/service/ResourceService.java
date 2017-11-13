package cn.gson.crm.service;

import cn.gson.crm.model.dao.ResourceDao;
import cn.gson.crm.model.domain.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    Logger logger = Logger.getLogger(ResourceService.class);

    @Autowired
    ResourceDao resourceDao;

    /**
     * 获取资源树
     *
     * @param status
     * @return
     */
    public Iterable<Resource> getResourceTree(Boolean status) {
        Iterable<Resource> root;
        if (status == null) {
            root = resourceDao.findByParentIsNull();
        } else {
            root = resourceDao.findByStatusAndParentIsNull(status, ResourceDao.WEIGHT_SORT);
        }
        this.buildTree(root, status);
        return root;
    }

    public Iterable<Resource> getResourceTree() {
        return this.getResourceTree(null);
    }

    private void buildTree(Iterable<Resource> root, Boolean status) {
        root.forEach(t -> {
            Iterable<Resource> children;

            if (status == null) {
                children = resourceDao.findByParent(t, ResourceDao.WEIGHT_SORT);
            } else {
                children = resourceDao.findByStatusAndParent(status, t, ResourceDao.WEIGHT_SORT);
            }

            children.forEach(c -> t.getChildren().add(c));

            // 此处递归
            buildTree(children, status);
        });
    }
}
