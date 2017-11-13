package cn.gson.crm.controller.system;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.common.DataGrid;
import cn.gson.crm.model.dao.ResourceDao;
import cn.gson.crm.model.domain.Resource;
import cn.gson.crm.service.ResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 资源管理控制器
 *
 * @author gson
 */
@Controller
@RequestMapping("/system/resource")
@Transactional(readOnly = true)
public class ResourceController {

    Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    ResourceDao resourceDao;

    @Autowired
    ResourceService resourceService;

    @RequestMapping
    public void index() {
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<Resource> list() {
        return new DataGrid<>(resourceDao.findAll(ResourceDao.WEIGHT_SORT));
    }

    @RequestMapping("/parent/tree")
    @ResponseBody
    public Iterable<Resource> parentTree() {
        return resourceService.getResourceTree();
    }

    @RequestMapping("form")
    public void form(Long id, Model model) {
        if (id != null) {
            ObjectMapper mapper = new ObjectMapper();
            Resource resource = resourceDao.findOne(id);
            try {
                model.addAttribute("resource", mapper.writeValueAsString(resource));
            } catch (JsonProcessingException e) {
                logger.error("json转换错误", e);
            }
            if (resource.getParent() != null) {
                model.addAttribute("parentId", resource.getParent().getId());
            }
        }
    }

    @RequestMapping({"/save", "/update"})
    @Transactional
    @ResponseBody
    public Object save(@Valid Resource resource, BindingResult br) {
        if (br.hasErrors()) {
            logger.error("对象校验失败：" + br.getAllErrors());
            return new AjaxResult(false).setData(br.getAllErrors());
        } else {
            return resourceDao.save(resource);
        }
    }

    @RequestMapping("/delete")
    @Transactional
    @ResponseBody
    public AjaxResult delete(Long id) {
        try {
            resourceDao.delete(id);
        } catch (Exception e) {
            return new AjaxResult().setMessage(e.getMessage());
        }
        return new AjaxResult();
    }
}
