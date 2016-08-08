package com.example.domain;

import com.example.constants.Authorities;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by Dmitrij on 24.07.2016.
 */
@Entity
public class Client extends MappedEntity {
    public Client() {
    }

    public Client(String firstName, String lastName, Address clientAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = Collections.singletonList(clientAddress);
    }

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zА-Я-_]*&")
    private String firstName;

    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zА-Я-_]*&")
    private String lastName;

    @OneToMany(mappedBy = "client")
    private List<Address> address;

    @OneToMany(mappedBy = "client")
    private List<ClientOrder> orders;

    private transient static Set<GrantedAuthority> authorities = new HashSet<>(2);

    static {
        authorities.add(new SimpleGrantedAuthority(Authorities.ROLE_CLIENT));
        authorities.add(new SimpleGrantedAuthority(Authorities.ROLE_VISITOR));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ClientOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ClientOrder> orders) {
        this.orders = orders;
    }

}
