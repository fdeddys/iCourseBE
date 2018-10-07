package com.ddabadi.model.dto;

import com.ddabadi.model.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PersonDto extends Person implements Serializable {
}
