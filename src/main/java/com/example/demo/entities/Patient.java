package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data //Générer les getters et les setters
@NoArgsConstructor //Constructeur sans paramétres
@AllArgsConstructor //Constructeur avec tous les paramétres
@ToString//méthode ToString()
public class Patient {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@NotNull//validation
@Size(min=5, max=15, message="Name incorrect")//validation
private String nom;
@Temporal(TemporalType.DATE)
@DateTimeFormat(pattern="yyyy-MM-d")//concerne spring pas database
@NotNull
private Date dateNaissance;
@NotNull
@DecimalMin("3")
private int score;

private boolean malade;
}
