package com.m2rs.userservice.model.api.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchCompanyRequest {

    private Long id;
    private String name;

}
