package com.api.springquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private IUserRepository api;

    @GetMapping("/all-users")
    @ResponseBody
    public List<AppUser> searchForAll (@RequestParam(value = "search", required = false) String searchString) {
        List<SearchCriteria> parameters = new ArrayList<SearchCriteria>();
        if (searchString!= null) {
            Pattern searchPattern = Pattern.compile("(\w+?)(:|<|>)(\w+?),");
            Matcher pathMatcher = searchPattern.matcher(searchString + ",");
            while (pathMatcher.find()) {
                parameters.add(new SearchCriteria(searchPattern.group(1),
                        searchPattern.group(2), searchPattern.group(3)));
            }
        }
        return api.searchUser(parameters);
    }
}
