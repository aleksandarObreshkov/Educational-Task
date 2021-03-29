package com.example.commands.delete;

import com.example.app.clients.StarshipClient;
import com.example.app.commands.delete.DeleteStarshipCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class DeleteStarshipCommandTest extends DeleteTest<DeleteStarshipCommand, StarshipClient> {

    @Override
    protected DeleteStarshipCommand setCommand(StarshipClient client) {
        return new DeleteStarshipCommand(client);
    }

    @Override
    protected StarshipClient createClient() {
        return Mockito.mock(StarshipClient.class);
    }

    @Test
    public void validDeleteCommand(){
        validCommand((client, id) ->
            verify(client).delete(id));
    }
}
