package com.springboot.test.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhoujian
 * @date 2020/5/25
 */
@Getter
@Setter
public class PipelineDto implements Serializable {

    /** 员工编号 */
    private String employeeNo;

    /** 员工姓名 */
    private String employeeName;

    /** 员工所属大区 */
    private String regionNo;

    /** 员工所属大区名称 */
    private String regionName;

    /** 员工所属分办编号 */
    private String branchNo;

    /** 员工所属分办名称 */
    private String branchName;

    /** 员工所属组编号 */
    private String teamNo;

    /** 员工所属组名称 */
    private String teamName;

    /** 员工职级 */
    private String rank;

    /** 职级类型 */
    private String rankType;

    /** 推荐-客户数 */
    private Integer reportCus;

    /** 推荐-职位数 */
    private Integer reportJob;

    /** 推荐-预测回款 */
    private BigDecimal reportPredict;

    /** 初试-客户数 */
    private Integer firstCus;

    /** 初试-职位数 */
    private Integer firstJob;

    /** 初试-预测回款 */
    private BigDecimal firstPredict;

    /** 复试-客户数 */
    private Integer secondCus;

    /** 复试-职位数 */
    private Integer secondJob;

    /** 复试-预测回款 */
    private BigDecimal secondPredict;

    /** offer-客户数 */
    private Integer offerCus;

    /** offer-职位数 */
    private Integer offerJob;

    /** offer-预测回款 */
    private BigDecimal offerPredict;

    /** 入职-客户数 */
    private Integer entryCus;

    /** 入职-职位数 */
    private Integer entryJob;

    /** 入职-预测回款 */
    private BigDecimal entryPredict;

    /** 实际回款-客户数 */
    private Integer backFeeCus;

    /** 实际回款-职位数 */
    private Integer backFeeJob;

    /** 实际回款 */
    private BigDecimal backFee;
}
