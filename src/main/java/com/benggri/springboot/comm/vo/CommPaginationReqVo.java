package com.benggri.springboot.comm.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommPaginationReqVo {

    /* 기본 파라미터 정보 */
    @JsonIgnore
    protected int pageNo;
    @JsonIgnore
    protected int limit;
    @JsonIgnore
    private int offset;
    /* 기본 파라미터 정보 */

    public int getOffset() {
        return (this.pageNo - 1) * limit;
    }
}
