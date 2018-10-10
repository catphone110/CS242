from unittest import TestCase
from Graph import *
import unittest

class TestGraph(TestCase):
    def test_setMovieData(self):
        graph = Graph()

    def test_setActorData(self):
        data = load_data("data.json")
        actors_data = data["Actor"]
        graph = Graph()
        graph.setActorData(actors_data)

    def test_setMovieNames(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        graph = Graph()
        graph.setMovieData(movies_data)

    def test_setActorNames(self):
        data = load_data("data.json")
        actors_data = data["Actor"]
        graph = Graph()
        graph.setActorData(actors_data)
        graph.setActorNames()

    def test_printAllActors(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setMovieNames()

    def test_printAllMovies(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.printAllMovies()

    def test_setPath(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.setPath()

    def test_printPaths(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.setPath()
        graph.printPaths()

    def test_getOldestActor(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.getOldestActor()

    def test_getMostGrossActor(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.getMostGrossActor()

    def test_getPrintAllMovieYears(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        years = graph.getPrintAllMovieYears()

    def test_printMoviesInYear(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.printMoviesInYear(2018)

    def test_getPrintAllActorYears(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.getPrintAllActorYears()

    def test_printActorsInYear(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.printActorsInYear(2018)

    def test_getMostGross_k(self):
        data = load_data("data.json")
        movies_data = data["Movie"]
        actors_data = data["Actor"]
        graph = Graph()
        graph.setMovieData(movies_data)
        graph.setActorData(actors_data)
        graph.setMovieNames()
        graph.setActorNames()
        graph.getMostGross_k(2)

if __name__ == '__main__':

    unittest.main()