package com.api.springquery;

import java.util.List;

public interface IUserRepository {

        List<AppUser> searchUser(List<SearchCriteria> params);

        void save(AppUser entity);
}