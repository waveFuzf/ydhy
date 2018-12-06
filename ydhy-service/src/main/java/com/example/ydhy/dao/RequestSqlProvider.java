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

import com.example.ydhy.entity.Request;
import com.example.ydhy.entity.RequestExample.Criteria;
import com.example.ydhy.entity.RequestExample.Criterion;
import com.example.ydhy.entity.RequestExample;
import java.util.List;
import java.util.Map;

public class RequestSqlProvider {

    public String countByExample(RequestExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("request");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(RequestExample example) {
        BEGIN();
        DELETE_FROM("request");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Request record) {
        BEGIN();
        INSERT_INTO("request");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getRoomId() != null) {
            VALUES("room_id", "#{roomId,jdbcType=INTEGER}");
        }
        
        if (record.getBeginTime() != null) {
            VALUES("begin_time", "#{beginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            VALUES("state", "#{state,jdbcType=VARCHAR}");
        }
        
        if (record.getScale() != null) {
            VALUES("scale", "#{scale,jdbcType=VARCHAR}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=VARCHAR}");
        }
        
        if (record.getParticipantsInfo() != null) {
            VALUES("participants_info", "#{participantsInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getTheme() != null) {
            VALUES("theme", "#{theme,jdbcType=VARCHAR}");
        }
        
        if (record.getIntroduce() != null) {
            VALUES("introduce", "#{introduce,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectByExample(RequestExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("user_id");
        SELECT("room_id");
        SELECT("begin_time");
        SELECT("end_time");
        SELECT("state");
        SELECT("scale");
        SELECT("is_delete");
        SELECT("participants_info");
        SELECT("theme");
        SELECT("introduce");
        FROM("request");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Request record = (Request) parameter.get("record");
        RequestExample example = (RequestExample) parameter.get("example");
        
        BEGIN();
        UPDATE("request");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            SET("user_id = #{record.userId,jdbcType=INTEGER}");
        }
        
        if (record.getRoomId() != null) {
            SET("room_id = #{record.roomId,jdbcType=INTEGER}");
        }
        
        if (record.getBeginTime() != null) {
            SET("begin_time = #{record.beginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{record.endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            SET("state = #{record.state,jdbcType=VARCHAR}");
        }
        
        if (record.getScale() != null) {
            SET("scale = #{record.scale,jdbcType=VARCHAR}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{record.isDelete,jdbcType=VARCHAR}");
        }
        
        if (record.getParticipantsInfo() != null) {
            SET("participants_info = #{record.participantsInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getTheme() != null) {
            SET("theme = #{record.theme,jdbcType=VARCHAR}");
        }
        
        if (record.getIntroduce() != null) {
            SET("introduce = #{record.introduce,jdbcType=VARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("request");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("user_id = #{record.userId,jdbcType=INTEGER}");
        SET("room_id = #{record.roomId,jdbcType=INTEGER}");
        SET("begin_time = #{record.beginTime,jdbcType=TIMESTAMP}");
        SET("end_time = #{record.endTime,jdbcType=TIMESTAMP}");
        SET("state = #{record.state,jdbcType=VARCHAR}");
        SET("scale = #{record.scale,jdbcType=VARCHAR}");
        SET("is_delete = #{record.isDelete,jdbcType=VARCHAR}");
        SET("participants_info = #{record.participantsInfo,jdbcType=VARCHAR}");
        SET("theme = #{record.theme,jdbcType=VARCHAR}");
        SET("introduce = #{record.introduce,jdbcType=VARCHAR}");
        
        RequestExample example = (RequestExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(Request record) {
        BEGIN();
        UPDATE("request");
        
        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getRoomId() != null) {
            SET("room_id = #{roomId,jdbcType=INTEGER}");
        }
        
        if (record.getBeginTime() != null) {
            SET("begin_time = #{beginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getState() != null) {
            SET("state = #{state,jdbcType=VARCHAR}");
        }
        
        if (record.getScale() != null) {
            SET("scale = #{scale,jdbcType=VARCHAR}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=VARCHAR}");
        }
        
        if (record.getParticipantsInfo() != null) {
            SET("participants_info = #{participantsInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getTheme() != null) {
            SET("theme = #{theme,jdbcType=VARCHAR}");
        }
        
        if (record.getIntroduce() != null) {
            SET("introduce = #{introduce,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(RequestExample example, boolean includeExamplePhrase) {
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