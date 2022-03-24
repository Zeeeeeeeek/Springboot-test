package com.zhejianglab.spring3common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author chenze
 * @date 2022/3/24
 */
@Data
@AllArgsConstructor
public class DataList<T> {
    private long total;
    private List<T> records;
}
