package com.olshevchenko.weatherapi.entity;

import com.olshevchenko.weatherapi.security.model.Role;
import com.olshevchenko.weatherapi.utils.PGSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

/**
 * @author Oleksandr Shevchenko
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PGSQLEnumType.class)
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq")
    private int id;

    private String email;

    private String password;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private Role role;

}
