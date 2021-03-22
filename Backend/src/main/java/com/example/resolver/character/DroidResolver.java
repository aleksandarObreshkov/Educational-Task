package com.example.resolver.character;

import graphql.kickstart.tools.GraphQLResolver;
import com.example.model.Droid;
import org.springframework.stereotype.Component;
import com.example.repositories.CharacterRepository;

@Component
// TODO Currently, there's no benefit to implementing GraphQLResolver, as that interface is used when loading the
// value of a field is not a trivial operation - friends, starships (see
// https://www.baeldung.com/spring-graphql#5-field-resolvers-for-complex-values). What you're doing is calling the
// allDroids and droid methods directly in QueryResolver and that would work even without implementing anything here.
public class DroidResolver extends CharacterResolver implements GraphQLResolver<Droid> {

    // TODO The content of this class is mostly identical to HumanResolver.
    public DroidResolver(CharacterRepository<Droid> repository) {
        super(repository);
    }


}
