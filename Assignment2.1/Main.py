from bs4 import BeautifulSoup
from urllib.request import urlopen
import re, json, ast
import json
from Graph import*
from flask import Flask
import plotly
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd

"""

https://plot.ly/organize/home
password: plotdragon

sudo lsof -i:8080
kill XXXX

http://localhost:5000/actors
http://localhost:5000/act?Actor%20Name=Bob
"""
from plotly.offline import iplot, init_notebook_mode
import plotly.graph_objs as go
import plotly.io as pio
from IPython.display import Image
import IPython
import plotly
import os
import numpy as np
import numpy

app = Flask(__name__)

actor_list = []
movie_list = []
actor_data = ""
movie_data = ""


def plot_weighted_heatMap(heat_map, weight):
    weighted_actor_names = []
    for i in range(len(actor_list)):
        if sum(heat_map[i]) > weight:
            weighted_actor_names.append(actor_list[i])

    weighted_heat_map = numpy.zeros((len(weighted_actor_names), len(weighted_actor_names)))
    for i in range(len(weighted_actor_names)):

        for j in range(len(weighted_actor_names)):
            weighted_heat_map[i][j] = heat_map[actor_list.index(weighted_actor_names[i])][
                actor_list.index(weighted_actor_names[j])]

    corr = weighted_heat_map
    mask = np.zeros_like(corr)
    mask[np.triu_indices_from(mask)] = True
    with sns.axes_style("white"):
        ax = sns.heatmap(corr, xticklabels=weighted_actor_names, yticklabels=weighted_actor_names, mask=mask, vmax=4,
                         square=True)
        plt.title('Weighted Actors Correlation Heat Map')
        plt.show()


def get_actor_corr_heatmap():
    heat_map = numpy.zeros((len(actor_data), len(actor_data)))
    for i in range(len(actor_data)):
        actor_name = actor_list[i]
        for m in actor_data[actor_name]['movies']:
            if m in movie_list:
                related_actors = movie_data[m]['actors']
                gross = movie_data[m]['box_office']
                for a in related_actors:
                    if a != actor_name and a in actor_list:
                        heat_map[i][actor_list.index(a)] += 1
    return heat_map

def plot_heatMap(heat_map):
    corr = heat_map
    mask = np.zeros_like(corr)
    mask[np.triu_indices_from(mask)] = True
    with sns.axes_style("white"):
        ax = sns.heatmap(corr,xticklabels=actor_list, yticklabels=actor_list, mask=mask, vmax=3, square=True, cmap="YlGnBu")
        plt.title('RAW Actors Correlation Heat Map')
        plt.show()

def plot_age_gross_density(age_min, age_max):
    ageGroupRange = list(range(age_min, age_max+1))
    ageGroupCount = numpy.zeros(len(ageGroupRange))
    ageGroupGross = numpy.zeros(len(ageGroupRange))
    for i in range(len(actor_data)):
        actor_name = actor_list[i]
        if actor_data[actor_name]['age'] in ageGroupRange:
            ageGroupCount[ageGroupRange.index(actor_data[actor_name]['age'])] += 1
        gross = 0
        for m in actor_data[actor_name]['movies']:
            if m in movie_list:
                related_actors = movie_data[m]['actors']
                gross += movie_data[m]['box_office']
        if actor_data[actor_name]['age'] in ageGroupRange:
            ageGroupGross[ageGroupRange.index(actor_data[actor_name]['age'])] = gross
    # put in graph and show
    d = {'Age Groups': ageGroupRange, 'Num of Actors': ageGroupCount, 'Gross': ageGroupGross}
    df = pd.DataFrame(data=d)
    cmap = sns.cubehelix_palette(dark=.3, light=.8, as_cmap=True)
    ax = sns.scatterplot(x="Age Groups",
                         y="Num of Actors",
                         hue="Gross",
                         size="Gross",
                         sizes=(40, 300),
                         palette=cmap,
                         data=df)
    plt.title('Age V.S. Gross Value Density Graph')
    plt.show()

import math
def calculateHub(heat_map):
    max = 0
    name = ""
    for i in range(heat_map.shape[0]):
        sum_1 = sum(heat_map[i])
        sum_2 = sum(heat_map[:,i])
        if sum_1 > max:
            max = sum_1
            name = actor_list[i]
        if sum_2 > max:
            max = sum_2
            name = actor_list[i]
    print("The hub star is: "+name)
    return name

def calculateAgeGroupMaxGross():
    gross = [0]*7
    age_group = ["20+", "30+", "40+", "50+", "60+", "70+", "80+"]

    for a in actor_list:
        if actor_data[a]["age"]<30:
            gross[0] += actor_data[a]["total_gross"]
        elif actor_data[a]["age"]<40:
            gross[1] += actor_data[a]["total_gross"]
        elif actor_data[a]["age"]<50:
            gross[2] += actor_data[a]["total_gross"]
        elif actor_data[a]["age"]<60:
            gross[3] += actor_data[a]["total_gross"]
        elif actor_data[a]["age"]<70:
            gross[4] += actor_data[a]["total_gross"]
        elif actor_data[a]["age"]<80:
            gross[5] += actor_data[a]["total_gross"]
        elif actor_data[a]["age"]<90:
            gross[6] += actor_data[a]["total_gross"]

    print(age_group)
    print(gross)
    print("\nThe age group that have the mac gross value is :" + str(age_group[gross.index(max(gross))]))
    print("With total gross: "+ str(gross.index(max(gross))))
    return 0

if __name__ == '__main__':
    data = load_data("data.json")
    actor_data = data[0]
    movie_data = data[1]

    actor_list = [*actor_data]
    movie_list = [*movie_data]

    plotly.tools.set_credentials_file(username='yayisama', api_key='Grnu4sPuYq89gZMO4kMg')

    heat_map = get_actor_corr_heatmap()


    c = 7

    while (c != 0):
        print("\n\n")
        c = int(input("Chose int value from one of the following:\n"
                      "1 = Show the hub Star:\n"
                      "2 = Display raw (all stars) Star correlation graph\n"
                      "3 = Display main hub Stars graph\n"
                      "4 = Show which age group have the most gross value?\n"
                      "5 = Display the age v.s. gross graph\n"
                      "0 = EXIST\n\n"
                      ))

        while c < 0 or c > 6:
            print()
            print("input is out if range, please try again:")
            c = int(input("Chose int value from one of the following:\n"
                          "1 = Show the hub Star:\n"
                          "2 = Display raw (all stars) Star correlation graph\n"
                          "3 = Display main hub Stars graph\n"
                          "4 = Show which age group have the most gross value?\n"
                          "5 = Display the age v.s. gross graph\n"
                          "0 = EXIST\n\n"
                          ))
        if c == 1:
            calculateHub(heat_map)

        if c == 2:
            plot_heatMap(heat_map)

        if c == 3:
            plot_weighted_heatMap(heat_map, 15)

        if c == 4:
            calculateAgeGroupMaxGross()

        if c == 5:
            plot_age_gross_density(20, 90)



