package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client")
    private Address address;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.phones = new ArrayList<>();
        setAddress(address);
        setPhones(phones);
    }



    @Override
    public Client clone() {
        Address clonedAddress = address != null ? address.clone(): null;
        List<Phone> newPhones = phones != null ? phones.stream().map(Phone::clone).toList() : phones;
        return new Client(this.id, this.name, clonedAddress, newPhones);
    }

    public void setAddress(Address address) {
        this.address = address;
        if (this.address != null){
            this.address.setClient(this);
        }
    }

    public void setPhones(List<Phone> phones) {
        if (phones!= null){
            this.phones.clear();
            phones.forEach(phone -> {
                phone.setClient(this);
                this.phones.add(phone);
            } );
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
