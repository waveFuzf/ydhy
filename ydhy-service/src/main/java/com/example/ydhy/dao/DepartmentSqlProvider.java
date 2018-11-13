package com.example.ydhy.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.example.ydhy.entity.Department;
import com.example.ydhy.entity.DepartmentExample.Criteria;
import com.example.ydhy.entity.DepartmentExample.Criterion;
import com.example.ydhy.entity.DepartmentExample;
import java.util.List;
import java.util.Map;

public class DepartmentSqlProvider {

    public String countByExample(DepartmentExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("department");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(DepartmentExample example) {
        BEGIN();
        DELETE_FROM("department");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Department record) {
        BEGIN();
        INSERT_INTO("department");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getDeptName() != null) {
            VALUES("dept_name", "#{deptName,jdbcType=VARCHAR}");
        }
        
        if (record.getIntrodecu() != null) {
            VALUES("introdecu", "#{introdecu,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getDirectorId() != null) {
            VALUES("director_id", "#{directorId,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(DepartmentExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("dept_name");
        SELECT("introdecu");
        SELECT("phone");
        SELECT("director_id");
        SELECT("status");
        SELECT("create_time");
        SELECT("update_time");
        SELECT("is_delete");
        FROM("department");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Department record = (Department) parameter.get("record");
        DepartmentExample example = (DepartmentExample) parameter.get("example");
        
        BEGIN();
        UPDATE("department");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getDeptName() != null) {
            SET("dept_name = #{record.deptName,jdbcType=VARCHAR}");
        }
        
        if (record.getIntrodecu() != null) {
            SET("introdecu = #{record.introdecu,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            SET("phone = #{record.phone,jdbcType=VARCHAR}");
        }
        
        if (record.getDirectorId() != null) {
            SET("director_id = #{record.directorId,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{record.status,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{record.isDelete,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("department");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("dept_name = #{record.deptName,jdbcType=VARCHAR}");
        SET("introdecu = #{record.introdecu,jdbcType=VARCHAR}");
        SET("phone = #{record.phone,jdbcType=VARCHAR}");
        SET("director_id = #{record.directorId,jdbcType=INTEGER}");
        SET("status = #{record.status,jdbcType=VARCHAR}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("is_delete = #{record.isDelete,jdbcType=VARCHAR}");
        
        DepartmentExample example = (DepartmentExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(Department record) {
        BEGIN();
        UPDATE("department");
        
        if (record.getDeptName() != null) {
            SET("dept_name = #{deptName,jdbcType=VARCHAR}");
        }
        
        if (record.getIntrodecu() != null) {
            SET("introdecu = #{introdecu,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getDirectorId() != null) {
            SET("director_id = #{directorId,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(DepartmentExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}