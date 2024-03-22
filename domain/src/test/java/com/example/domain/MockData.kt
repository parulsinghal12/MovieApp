package com.example.domain

import com.example.domain.model.Genre
import com.example.domain.model.Movie
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList

object MockData {
    val mockMovieList = MovieList(
        movies = listOf(
            Movie(
                backdropPath = "/gJL5kp5FMopB2sN4WZYnNT5uO0u.jpg",
                id = 1011985,
                originalLanguage = "en",
                overview = "Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.",
                popularity = 1652.671,
                posterPath = "https://image.tmdb.org/t/p/w500//kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg",
                releaseDate = "2024-03-02",
                title = "Kung Fu Panda 4",
                rating = 7.0,
                voteCount = 74
            ),
            Movie(
                backdropPath = "/xvk5AhfhgQcTuaCQyq3XqAnhEma.jpg",
                id = 848538,
                originalLanguage = "en",
                overview = "When the plots of reclusive author Elly Conway's fictional espionage novels begin to mirror the covert actions of a real-life spy organization, quiet evenings at home become a thing of the past. Accompanied by her cat Alfie and Aiden, a cat-allergic spy, Elly races across the world to stay one step ahead of the killers as the line between Conway's fictional world and her real one begins to blur.",
                popularity = 1636.918,
                posterPath = "https://image.tmdb.org/t/p/w500//95VlSEfLMqeX36UVcHJuNlWEpwf.jpg",
                releaseDate = "2024-01-31",
                title = "Argylle",
                rating = 6.118,
                voteCount = 514
            )
        )
    )

    val mockMovieDetail = MovieDetail(
        budget = 85000000,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 12, name = "Adventure"),
            Genre(id = 16, name = "Animation"),
            Genre(id = 35, name = "Comedy"),
            Genre(id = 10751, name = "Family")
        ),
        homepage = "https://www.dreamworks.com/movies/kung-fu-panda-4",
        id = 1011985,
        originalLanguage = "en",
        overview = "Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.",
        posterPath = "https://image.tmdb.org/t/p/w500/wkfG7DaExmcVsGLR4kLouMwxeT5.jpg",
        productionCompanies = listOf(
            "DreamWorks Animation",
            "Universal Pictures",
            "Pearl Studio"
        ),
        revenue = 176000000,
        runtime = 94,
        status = "Released",
        title = "Kung Fu Panda 4",
        video = false,
        rating = 6.9,
        voteCount = 224
    )

}