package com.example.app;

import java.io.File;

public class FileCreator {
     private File fileCharacters = new File("C:/Users/I542072/app/src/main/resources/dummyCharacter.json");
     private File fileMovies = new File("C:/Users/I542072/app/src/main/resources/dummyMovies.json");
     private File fileStarships = new File("C:/Users/I542072/app/src/main/resources/dummyStarships.json");

    public File getFileCharacters() {
        return fileCharacters;
    }

    public File getFileMovies() {
        return fileMovies;
    }

    public File getFileStarships() {
        return fileStarships;
    }
}
