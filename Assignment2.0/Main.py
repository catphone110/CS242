from bs4 import BeautifulSoup
from urllib.request import urlopen
import re, json, ast
import json
from scrap import*
from Graph import*

if __name__ == "__main__":
    data = load_data("data.json")
    movies_data = data["Movie"]
    actors_data = data["Actor"]

    graph = Graph()
    graph.setMovieData(movies_data)
    graph.setActorData(actors_data)
    graph.setMovieNames()
    graph.setActorNames()
    graph.setPath()

    c = 0

    while (c != 8):
        print()
        c = int(input("Chose int value from one of the following:\n"
                      "1 = How much a movie has grossed\n"
                      "2 = List which movies an actor has worked in\n"
                      "3 = List which actors worked in a movie\n"
                      "4 = List the top X actors with the most total grossing value\n"
                      "5 = List the oldest X actors\n"
                      "6 = List all the movies for a given year\n"
                      "7 = List all the actors for a given year\n"
                      "8 = EXIST\n\n"
                      ))

        while c < 1 or c > 8:
            print()
            print("input is out if range, please try again:")
            c = int(input("Chose int value from one of the following:\n"
                          "1 = How much a movie has grossed\n"
                          "2 = List which movies an actor has worked in\n"
                          "3 = List which actors worked in a movie\n"
                          "4 = List the top X actors with the most total grossing value\n"
                          "5 = List the oldest X actors\n"
                          "6 = List all the movies for a given year\n"
                          "7 = List all the actors for a given year\n"
                          "8 = EXIT\n\n"
                          ))
        if c == 1:
            choice = input("Do you want to see the list of movies？ (y/n): \n")
            if choice == 'y':
                graph.printAllMovies()
            name = input("Please enter the name of movie for its gross value:\n")
            if name in graph.movieNames:
                print("Gross of " + name + " is " + str(graph.movieData[graph.movieNames.index(name)]["gross"]))

        if c == 2:
            choice = input("Do you want to see the list of actors？ (y/n): \n")
            if choice == 'y':
                graph.printAllActors()
            name = input("Please enter the name of actor for his/her movie list:\n")
            if name in graph.actorNames:
                print("Movies list of " + name + " : " + str(graph.actorData[graph.actorNames.index(name)]["movies"]))

        if c == 3:
            choice = input("Do you want to see the list of movies？ (y/n): \n")
            if choice == 'y':
                graph.printAllMovies()
            name = input("Please enter the name of movie for its gross value:\n")
            if name in graph.movieNames:
                print(
                    "Actor List for " + name + " is :" + str(graph.movieData[graph.movieNames.index(name)]["starring"]))

        # "4 = List the top X actors with the most total grossing value\n"
        if c == 4:
            k = int(input("How many top actors: \n"))
            if k<1 or k>250:
                print("Invalid Input")
            else:
                graph.getMostGross_k(k)

        if c == 5:
            graph.getOldestActor()

        if c == 6:
            choice = input("Do you want to see the possible movie years？ (y/n): \n")
            years = []
            if choice == 'y':
                years = set(graph.getPrintAllMovieYears())
            year = int(input("Please enter year to see movie list:\n"))
            if year in years:
                graph.printMoviesInYear(year)

        if c == 7:
            choice = input("Do you want to see the possible actor years？ (y/n): \n")
            years = []
            if choice == 'y':
                years = graph.getPrintAllActorYears()
            year = int(input("Please enter year to see actor list:\n"))
            if year in years:
                graph.printActorsInYear(year)


