package site.dopplerxd.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName problem_case
 */
@TableName(value ="problem_case")
@Data
public class ProblemCase implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目id
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 测试数据，json数组
     */
    @TableField(value = "cases")
    private String cases;

    /**
     * 
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    /**
     * 
     */
    @TableField(value = "gmt_modified")
    private Date gmt_modified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}