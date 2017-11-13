package cn.gson.crm.controller.system;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.common.DataGrid;
import cn.gson.crm.model.dao.MemberDao;
import cn.gson.crm.model.dao.RoleDao;
import cn.gson.crm.model.domain.Member;
import cn.gson.crm.model.domain.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * 用户管理控制器
 *
 * @author gson
 */
@Controller
@RequestMapping("/system/member")
@Transactional(readOnly = true)
public class MemberController {

    Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    MemberDao memberDao;

    @Autowired
    RoleDao roleDao;

    /**
     * 超级管理员id
     */
    @Value("${crm.system.super-user-id}")
    Long superUserId;

    @RequestMapping
    public void index() {
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGrid<Member> list(int page, int rows, String userName, String realName, String telephone) {
        PageRequest pr = new PageRequest(page - 1, rows, Direction.DESC, "id");
        Page pageData = memberDao.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (isNotEmpty(userName)) {
                predicates.add(cb.like(root.get("userName"), "%" + userName + "%"));
            }
            if (isNotEmpty(realName)) {
                predicates.add(cb.like(root.get("realName"), "%" + realName + "%"));
            }
            if (isNotEmpty(telephone)) {
                predicates.add(cb.equal(root.get("telephone"), telephone));
            }

            query.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pr);

        return new DataGrid<>(pageData);

    }

    @RequestMapping("/form")
    public void form(Long id, Model model) {
        if (id != null) {
            ObjectMapper mapper = new ObjectMapper();
            Member member = memberDao.findOne(id);
            try {
                model.addAttribute("member", mapper.writeValueAsString(member));
            } catch (JsonProcessingException e) {
                logger.error("json转换错误", e);
            }
        }
    }

    @RequestMapping("/check")
    @ResponseBody
    public boolean check(String userName) {
        return memberDao.countByUserName(userName) == 0;
    }

    @RequestMapping("/roles")
    @ResponseBody
    public List<Role> roles() {
        return roleDao.findByStatus(true);
    }

    @RequestMapping({"/save", "/update"})
    @Transactional
    @ResponseBody
    public AjaxResult save(@Valid Member member, Long[] roles, BindingResult br) {
        if (br.hasErrors()) {
            logger.error("对象校验失败：" + br.getAllErrors());
            return new AjaxResult(false).setData(br.getAllErrors());
        } else {
            if (member.getId() != null) {
                // 不在这里更新角色和密码
                Member orig = memberDao.findOne(member.getId());
                // 理论上这里一定是要找得到对象的
                if (orig != null) {
                    member.setPassword(orig.getPassword());
                }
            } else {
                // 默认密码
                member.setPassword(DigestUtils.sha256Hex("0000"));
            }

            //处理角色的关联
            if (roles != null && roles.length > 1) {
                List<Role> rolesList = new ArrayList<>();
                for (Long rid : roles) {
                    if (rid != null) {
                        rolesList.add(roleDao.findOne(rid));
                    }
                }
                member.setRoles(rolesList);
            }

            memberDao.save(member);

            return new AjaxResult();
        }
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping("/password/reset")
    @Transactional
    @ResponseBody
    public AjaxResult resetPassword(Long id) {
        Member member = memberDao.findOne(id);
        member.setPassword(DigestUtils.sha256Hex("0000"));
        memberDao.save(member);
        return new AjaxResult();
    }

    @RequestMapping("/delete")
    @Transactional
    @ResponseBody
    public AjaxResult delete(Long id) {
        try {
            if (superUserId != id) {
                memberDao.delete(id);
            } else {
                return new AjaxResult(false, "管理员不能删除！");
            }
        } catch (Exception e) {
            return new AjaxResult(false).setMessage(e.getMessage());
        }
        return new AjaxResult();
    }
}
