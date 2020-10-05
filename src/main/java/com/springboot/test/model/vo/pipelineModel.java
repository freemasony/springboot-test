package com.springboot.test.model.vo;

import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhoujian
 * @date 2020/5/21
 */
@Getter
@Setter
public class pipelineModel extends BaseRowModel {

    /** 推荐-客户数 */
    private Integer reportCus;
    /** 推荐-职位数 */
    private Integer reportJob;

    /** offer-客户数 */
    private Integer offerCus;
    /** offer-职位数 */
    private Integer offerJob;



    public static List<pipelineModel> build(List<PipelineDto> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }
        List<pipelineModel> models = new ArrayList<pipelineModel>();
        Integer index = 1;
        for (PipelineDto dto : dtos) {
            models.add(build(dto, index));
            index++;
        }
        return models;

    }

    public static pipelineModel build(PipelineDto dto, Integer index) {
        pipelineModel model = new pipelineModel();
        model.setReportCus(dto.getReportCus());
        model.setReportJob(dto.getReportJob());

        return model;
    }

}
