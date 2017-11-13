package cn.gson.crm.controller.attence;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by aplus on 2017/11/12.
 */
@Controller
@RequestMapping("/staff/attence")
@Transactional(readOnly = true)
public class AttenceController {

    @RequestMapping
    public void index() {
    }
}
