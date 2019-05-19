package com.zk.springcloudzkservice.web;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Test {

    public static void main(String[] args) {
        User user = new User();
        List<String> list = Collections.emptyList();
        user.setList(list);
        Optional<User> op = Optional.of(user);
//        boolean s = op.map(User::getList).map().isPresent();
        System.out.println("---------");
    }

    public static class User {

        private List<String> list;

        private String name;


        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
