package io.chaoshua.modules.background.controller.agent;

import java.util.Arrays;
import java.util.Map;

import io.chaoshua.common.utils.ShiroUtils;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.service.agent.AgentService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;


/**
 * 代理平台表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-22 10:33:42
 */
@RestController
@RequestMapping("background/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:agent:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = agentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:agent:info")
    public R info(@PathVariable("id") Long id) {
        AgentEntity agent = agentService.selectById(id);

        return R.ok().put("agent", agent);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:agent:save")
    public R save(@RequestBody AgentEntity agent) {
        agentService.insertAgent(agent);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:agent:update")
    public R update(@RequestBody AgentEntity agent) {
        agentService.updateById(agent);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:agent:delete")
    public R delete(@RequestBody Long[] ids) {
        agentService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
