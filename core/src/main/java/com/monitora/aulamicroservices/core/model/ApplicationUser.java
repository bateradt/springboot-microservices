package com.monitora.aulamicroservices.core.model;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationUser implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "The field 'username' is required")
    @Column(nullable = false)
    private String username;

    @NotNull(message = "The field 'password' is required")
    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @NotNull(message = "The field 'role' is required")
    @Column(nullable = false)
    private String role = "USER";

    public ApplicationUser(@NotNull ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.password = applicationUser.getPassword();
        this.username = applicationUser.getUsername();
        this.role = applicationUser.getRole();
    }

}
