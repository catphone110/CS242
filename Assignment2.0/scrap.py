from bs4 import BeautifulSoup
from urllib.request import urlopen
import re, json, ast
import logging
import json
from Graph import *
import time


def tryGetFilmUrl(raw_url):
    try:
        page = urlopen(raw_url)
    except:
        logging.error("Error in urlopen(raw_url)")
        return None

    # get soup object:
    soup = BeautifulSoup(page, "html.parser")

    # get table content
    table = soup.find("a", href=re.compile("filmography"))
    if table == None:
        logging.warning("none filmography link found")
        return None
    movie_url = None
    if (table["href"]):
        movie_url = str("https://en.wikipedia.org" + table["href"])
    print(movie_url)
    return movie_url


def getActorsFromMovie(movie_url, movie_obj):
    try:
        page = urlopen(movie_url)
    except:
        logging.error("Error in urlopen(movie_url)")
        return None
    # not open url
    if movie_url == "" or not movie_url:
        logging.info("Cannot open movie url.")
        return None
    soup = BeautifulSoup(page, "html.parser")
    if not soup:
        logging.info("Cannot open soup url.")
        return None
    table = soup.find("table", {"class": "infobox vevent"})

    # not find table
    if not table:
        logging.info("Cannot find film table from url.")
        return None
    actors = list()
    raw_actors = None
    box_office = ""
    for row in table.findAll("tr"):
        if row.findAll(string=re.compile("Starring")):
            raw_actors = row.findAll('a')
        if row.findAll(string=re.compile("Box office")):
            box_office = row.find("td").text
            if "["  in box_office:
                box_office = box_office[0: box_office.index("[")]
                if (movie_obj):
                    movie_obj.setBoxOffice(box_office)
            print(box_office)

    gross = 0
    print("box_office"+box_office)
    if not box_office == "":
        multiplier = 1
        box_office = box_office.lower()
        if "million" in box_office:
            multiplier = 1000000
        box_office = re.sub(re.compile('[^0-9]'), "", box_office)
        gross = float(box_office)*multiplier

    print("gross = "+str(gross))
    movie_obj.setGross(gross)

    for raw_actor in raw_actors:
        if raw_actor:
            url_temp = str("https://en.wikipedia.org" + raw_actor["href"]).replace("(actor)", "")
            film_url = tryGetFilmUrl(url_temp) if tryGetFilmUrl(url_temp) else ""
            newActor = Actor(str(raw_actor["title"]).replace(" (actor)", ""),url_temp,film_url)
            newActor.increaseGross(gross)
            #newActor.setActorAgeFromActorUrl
            actors.append(newActor)

    return actors

def setActorAgeFromActorUrl(url, actor):
    try:
        page = urlopen(url)
    except:
        logging.error("Error in urlopen(url)")
        return None
    # get soup object:
    soup = BeautifulSoup(page, "html.parser")
    if not soup:
        logging.info("Cannot open soup url.")
        return None

    table = soup.find("table", {"class": "infobox biography vcard"})
    # not find table
    if not table:
        logging.info("Cannot find film table from url.")
        return None
    actors = list()
    birthDay = ""
    for row in table.findAll("tr"):
        if row.findAll(string=re.compile("Born")):
            try:
                birthDay = row.find("span", {"class": "bday"}).text
            except:
                logging.info("Cannot find birthDay info.")
                return None
            # print(birthDay)
    brithYear = -1
    if not birthDay == "":
        try:
            brithYear = int(birthDay[0:4])
        except:
            logging.info("Actor Brith Year format wrong.")

    if actor:
        actor.setBirthYear(brithYear)



def getMovieFromFilmUrl(url):
    try:
        page = urlopen(url)
    except:
        logging.error("Error in urlopen(url)")
        return None

    soup = BeautifulSoup(page, "html.parser")
    # not open url
    if not soup:
        logging.info("Cannot open movie url.")
        return None
    table = soup.find("table", {"class" : "wikitable sortable"}).find("tbody").findAll("tr")
    last_hold_year = ""
    # not find table
    if not table:
        logging.info("Cannot find film table from url.")
        return None
    movies = list()
    for tr in table:
        x = tr.find("td")
        if x:
            year = x.text if (x.text)<=str(2018) else last_hold_year
            last_hold_year = year
            if(not x.find("i")):
                x = x.find_next_sibling()
            x_i = x.find("i")
            if(x_i ):
                a_i = x_i.find("a")
                if(a_i):
                    print(a_i["title"])
                    print(a_i["href"])
                    print(int(year))
                    movies.append(Movie(a_i["title"], "https://en.wikipedia.org"+a_i["href"], int(year.replace('\n',""))))
    return movies


def star_scrap():
    json_data = {}

    actorList = []
    actor_names = []
    movieList = []

    start_url = "https://en.wikipedia.org/wiki/Michael_Caine_filmography"
    movies = getMovieFromFilmUrl(start_url)
    movieList.extend(movies)
    print(len(movieList))
    i = 0


    actor_i = 0
    TEST_SIZE = 20
    #while(len(actorList)<250 or i<len(movieList)):
    while (len(actorList) < TEST_SIZE and i < len(movieList)):
        movie_i = movieList[i]
        actors = getActorsFromMovie(movie_i.url, movie_i)
        #movie_i.setId(i)
        if actors:
            if i<40:
                for act in actors:
                    movie_i.appendStar(act.name)
            else:
                for act in actors:
                    setActorAgeFromActorUrl(act.url, act)
                    movie_i.appendStar(act.name)
                    if len(actorList)<=250:
                        if (not act == None) and (not act.name in actor_names):
                            actor_names.append(act.name)
                            print(act.name)
                            actorList.append(act)
                            act.setId(actor_i)
                            actor_i+=1
                            act.appendMovie(movie_i.name)
                            act.increaseGross(movie_i.gross)
                    elif (not act == None) and (act.name in actor_names):
                        actor_index = actor_names.index(act.name)
                        actorList[actor_index].appendMovie(movie_i.name)
                        actorList[actor_index].increaseGross(movie_i.gross)
        print("len(actorList)" + str(len(actorList)))
        i += 1
        print("i = "+str(i))
    


    M = []
    i = 0
    for movie in movieList:
        movie.setId(i)
        i+=1
        M.append(movie.to_json())
    #print(M)

    A = []
    #i = 0
    for actor in actorList:
        #actor.setId(i)
        #i += 1
        A.append(actor.to_json())
    json_data['Movie'] = M
    json_data['Actor'] = A

    with open('temp_3.json', 'w') as outfile:
        json.dump(json_data, outfile, indent=4)
    """
    for movie in movies:
        json.dump(movie.to_json(), json_file)
    
    url = "https://en.wikipedia.org/wiki/Inception"
    movies = getActorFromMovie(url, None)

    """


if __name__ == "__main__":
    start_time = time.time()
    star_scrap()
    print("--- %s seconds ---" % (time.time() - start_time))

