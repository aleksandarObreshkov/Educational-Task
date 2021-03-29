package com.example.commands.delete;

import com.example.app.clients.MovieClient;
import com.example.app.commands.delete.DeleteMovieCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class DeleteMovieCommandTest extends DeleteTest<DeleteMovieCommand, MovieClient> {

    @Override
    protected DeleteMovieCommand setCommand(MovieClient client) {
        return new DeleteMovieCommand(client);
    }

    @Override
    protected MovieClient createClient() {
        return Mockito.mock(MovieClient.class);
    }

    @Test
    public void validDeleteCommand(){
        validCommand((client, id) ->
                verify(client).delete(id));
    }

}
