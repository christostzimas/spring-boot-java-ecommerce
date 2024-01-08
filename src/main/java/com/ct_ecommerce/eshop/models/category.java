package com.ct_ecommerce.eshop.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


//@Entity
//@Table(name="categories")
public class category {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "name")
    private /*@NotBlank*/ String name;
    private /*@NotBlank*/ String image_path;
    private /*@NotBlank*/ String slug;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String imagePath) {
        this.image_path = imagePath;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
