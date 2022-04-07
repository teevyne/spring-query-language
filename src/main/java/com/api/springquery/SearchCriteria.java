package com.api.springquery;

import lombok.Data;

@Data
public class SearchCriteria {

    private String searchKey;
    private String searchOperation;
    private Object searchValue;

}