from bs4 import BeautifulSoup
from urllib.request import urlopen
import re, json, ast
import json
from scrap import*


class Actor:

    name = ""
    i = -1
    url = ""
    filmUrl = ""
    birthYear = -1
    movies = []
    gross = 0

    def __init__(self, name, url, furl):
        self.name = name
        self.i = -1
        self.url = url
        self.filmUrl = furl
        self.birthYear = -1
        self.movies = []
        self.gross = 0

    def to_json(self):
        j = {}
        j['name'] = self.name
        j['i'] = self.i
        j['url'] = self.url
        j['filmUrl'] = self.filmUrl
        j['birthYear'] = self.birthYear
        j['movies'] = self.movies
        j['gross'] = 0
        return ast.literal_eval(json.dumps(j))

    def appendMovie(self, movie):
        self.movies.append(movie)

    def increaseGross(self, gross):
        self.gross += gross

    def setId(self, id):
        self.i = id

    def setBirthYear(self, year):
        self.birthYear = year


class Movie:
    name = ""
    i = -1
    url = ""
    year = -1
    box_office = ""
    gross = 0
    starring = []

    def __init__(self, name, url, year):
        self.name = name
        self.i = -1
        self.url = url
        self.year = year
        self.box_office = ""
        self.gross = 0
        self.starring = []

    def setId(self, id):
        self.i = id

    def setBoxOffice(self, bf):
        self.box_office = bf

    def appendStar(self, star):
        self.starring.append(star)

    def setGross(self, gross):
        self.gross = gross

    def to_json(self):
        j = {}
        j['name'] = self.name
        j['i'] = self.i
        j['url'] = self.url
        j['year'] = self.year
        j['gross'] = self.gross
        j['starring'] = self.starring
        return ast.literal_eval(json.dumps(j))


class Path:
    actor = None
    movie = None
    value = 0

    def __init__(self, actor, movie, value):
        self.actor = actor
        self.movie = movie
        self.value = value

    def printPath(self):
        print("["+ str(self.movie)+"]-----------"+ str(self.value)+"------------(" + self.actor + ")")


class Graph:

    movieData = None
    actorData = None
    movieNames = None
    actorNames = None
    num_m = 0
    num_a = 0
    paths = []

    def __init__(self):
        movieData = None
        actorData = None
        movieNames = None
        actorNames = None
        num_m = 0
        num_a = 0
        path = []

    def setMovieData(self, data):
        self.movieData = data

    def setActorData(self, data):
        self.actorData = data

    def setMovieNames(self):
        if self.movieData:
            self.movieNames = [m["name"] for m in self.movieData]
            self.num_m = len(self.movieNames)

    def setActorNames(self):
        if self.actorData:
            self.actorNames = [a["name"] for a in self.actorData]
            self.num_a = len(self.actorNames)

    def printAllActors(self):
        print("All actor names ========================== ")
        for a_name in self.actorNames:
            print(a_name)
        print()

    def printAllMovies(self):
        print("All movie names ========================== ")
        for m_name in self.movieNames:
            print(m_name)
        print()

    def setPath(self):
        self.path = [[0] * self.num_m for i in range(self.num_a)]
        movie_gross_list = [m["gross"] for m in self.movieData]
        for actor_i in range(0, self.num_a):
            for movie_i in range(0, self.num_m):
                if self.movieNames[movie_i] in self.actorData[actor_i]["movies"]:
                    path_i = Path(self.actorNames[actor_i], self.movieNames[movie_i], movie_gross_list[movie_i])
                    #self.path[actor_i][movie_i] = movie_gross_list[movie_i]
                    self.paths.append(path_i)
        #for actor_i in range(0, self.num_a):
        #    print(self.path[actor_i])

    def printPaths(self):
        for p in self.paths:
            p.printPath()

    def getOldestActor(self):
        oldest = 2018
        actor_index = 0
        for i in range(0, self.num_a):
            if self.actorData[i]["birthYear"]>0 and self.actorData[i]["birthYear"]<oldest:
                oldest = self.actorData[i]["birthYear"]
                actor_index = i
        print("Oldest actor's name: "+ self.actorData[actor_index]["name"])
        print("Oldest actor's birth year: "+ str(self.actorData[actor_index]["birthYear"]))

    def getMostGrossActor(self):
        value = 0
        index = 0
        for actor in self.actorData:
            if actor["gross"]>value:
                value = actor["gross"]
                index = actor["i"]
        print("The highest gross value is "+ str(value) +", the actor name is "+self.actorData[index]["name"])

    def getPrintAllMovieYears(self):
        years = set([m["year"] for m in self.movieData])
        for y in years:
            print(y)
        return years

    def printMoviesInYear(self, year):
        for m in self.movieData:
            if m["year"] == year:
                print(m["name"])

    def getPrintAllActorYears(self):
        years = set([a["birthYear"] for a in self.actorData]) - {-1}
        for y in years:
            print(y)
        return years

    def printActorsInYear(self, year):
        for a in self.actorData:
            if a["birthYear"] == year:
                print(a["name"])

    def getMostGross_k(self, k):
        gross = set([a["gross"] for a in self.actorData])
        gross_k = sorted(gross, key = int, reverse = True)[0:k]
        print("Top gross amounts are : "+ str(gross_k))
        for actor in self.actorData:
            if actor["gross"] in gross_k:
                gross_k.remove(actor["gross"])
                print("Actor name: " + actor["name"] +" | Gross = "+ str(actor["gross"]))




def update_data():
    json_data = open("temp_2.json").read()
    data = json.loads(json_data)
    movies_data = data["Movie"]
    actors_data = data["Actor"]
    actor_nameList = [d["name"] for d in actors_data]
    for m in movies_data:
        for star in m["starring"]:
            if star in actor_nameList:
                actors_data[actor_nameList.index(star)]["gross"] += m["gross"]
                if m["name"] not in actors_data[actor_nameList.index(star)]["movies"]:
                    actors_data[actor_nameList.index(star)]["movies"].append(m["name"])

    for actor in actors_data:
        print(actor)

    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)


def load_data(file_name):
    json_data = open(file_name).read()
    data = json.loads(json_data)
    return data


