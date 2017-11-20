package me.soulyana.lesson12.repositories;


import me.soulyana.lesson12.entitites.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
