package com.john.java.stream;

import java.util.stream.Stream;

public class User {


    private int id;
    private String name;

    public static Stream<User> users
            = Stream.of(
                    new User(1, "John"), new User(2, "lijun"),
                    new User(10, "Rod"), new User(12, "Lee"));

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
