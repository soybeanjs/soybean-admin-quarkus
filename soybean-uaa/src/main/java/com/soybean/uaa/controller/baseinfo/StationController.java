package com.soybean.uaa.controller.baseinfo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.soybean.framework.commons.annotation.log.SysLog;
import com.soybean.framework.commons.util.BeanUtilPlus;
import com.soybean.uaa.domain.dto.StationPageDTO;
import com.soybean.uaa.domain.dto.StationSaveDTO;
import com.soybean.uaa.domain.entity.baseinfo.Station;
import com.soybean.uaa.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位管理
 *
 * @author wenxina
 * @date 2022/07/12
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    /**
     * 查询岗位
     *
     * @param params 参数个数
     * @return {@link IPage}<{@link Station}>
     */
    @GetMapping
    public IPage<Station> query(StationPageDTO params) {
        return stationService.findStationPage(params, params);
    }

    /**
     * 添加岗位
     *
     * @param dto dto
     */
    @PostMapping
    @SysLog(value = "添加岗位")
    public void add(@Validated @RequestBody StationSaveDTO dto) {
        stationService.save(BeanUtil.toBean(dto, Station.class));
    }

    /**
     * 编辑岗位
     *
     * @param id  id
     * @param dto dto
     */
    @PutMapping("/{id}")
    @SysLog(value = "编辑岗位")
    public void edit(@PathVariable Long id, @Validated @RequestBody StationSaveDTO dto) {
        stationService.updateById(BeanUtilPlus.toBean(id, dto, Station.class));
    }

    /**
     * 删除岗位
     *
     * @param id id
     */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除岗位")
    public void del(@PathVariable Long id) {
        stationService.removeById(id);
    }

}
