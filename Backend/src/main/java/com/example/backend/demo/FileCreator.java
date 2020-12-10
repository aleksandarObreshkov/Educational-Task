package com.example.backend.demo;

import java.io.File;

public class FileCreator {
     private File fileCharacters = new File("/Users/i542072/IdeaProjects/TrainingTaskParent/Backend/src/main/resources/dummyData/dummyCharacters.json");
     private File fileMovies = new File("/Users/i542072/IdeaProjects/TrainingTaskParent/Backend/src/main/resources/dummyData/dummyMovies.json");
     private File fileStarships = new File("/Users/i542072/IdeaProjects/TrainingTaskParent/Backend/src/main/resources/dummyData/dummyStarships.json");

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
