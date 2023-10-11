package com.example.src;

import com.example.src.Kind;

public class Pet {
    private String id;
    private String name;
    private Kind kind;
    private String profileText;

    public Pet(String id, String name, Kind kind, String profileText) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.profileText = profileText;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Kind getKind() {
        return kind;
    }

    public String getProfileText() {
        return profileText;
    }
}
