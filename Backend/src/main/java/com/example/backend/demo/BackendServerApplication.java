package com.example.backend.demo;

import model.Character;
import model.Droid;
import model.Human;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EntityScan(value = "model")
public class BackendServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendServerApplication.class, args);

        /*
        Human palpetine = new Human("Luke", 50, true);


        Human man = new Human();
        man.setAge(40);
        man.setName("Han Solo");
        man.setForceUser(true);
        List<Character> friends = new ArrayList<>();
        friends.add(palpetine);

        man.setFriends(friends);

        EntityManagerFactory factory= Persistence.createEntityManagerFactory("PostgreJPA");
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();
        //manager.persist(d);
        manager.persist(man);
        manager.getTransaction().commit();
        manager.close();

         */


    }

}
