package com.scaler.productservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Product extends BaseModel implements Serializable {

    private String title;
    private Double price;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private String description;
    private String imageUrl;
}
