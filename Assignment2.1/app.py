from flask import Flask, session, redirect, url_for, escape, request, jsonify

from Graph import*
from flask import Flask
import flask
app = Flask(__name__)

data = None
actor_list = []
movie_list = []
actor_data = ""
movie_data = ""
"""
useful links [flask]：
https://blog.miguelgrinberg.com/post/designing-a-restful-api-with-python-and-flask
https://scotch.io/bar-talk/processing-incoming-request-data-in-flask

sudo lsof -i:8080
kill XXXX

http://localhost:5000/actors
http://localhost:5000/act?Actor%20Name=Bob
"""


@app.errorhandler(404)
def not_found(error):
    return flask.make_response(jsonify({'error': 'Not found'}), 404)


@app.route('/')
def index():
    return "CS 242-Assignment 2.1\n Yayi"


# http://localhost:5000/actor/all
@app.route('/actor/all', methods=['GET'])
def get_all_actor():
    return jsonify(actor_list)


# http://localhost:5000/actor/James Whitmore
@app.route('/actor/<string:actor_name>', methods=['GET'])
def get_actor_information(actor_name):
    try:
        x = actor_data[actor_name]
    except:
        return flask.make_response(jsonify({'error': 'Not found: ' + actor_name}), 404)
    return jsonify(x)


# http://localhost:5000/actor?name=Faye&age=76
# http://localhost:5000/actor?name=Faye&age=100
@app.route('/actor', methods=['GET'])
def get_actor_by_condition():
    name = request.args.get('name')
    age = request.args.get('age')
    for n in actor_list:
        if (name in n) or name == n:
            if str(actor_data[n]["age"]) == str(age):
                return jsonify(actor_data[n])
    return flask.make_response(jsonify({'error': 'Not found: ' + name + " & " + age}), 404)


# http://localhost:5000/actor/?name1=Pual&name2=James
# http://localhost:5000/actor/?age=61
# http://localhost:5000/actor/?name1=Pul&name2=Jams
@app.route('/actor/', methods=['GET'])
def get_1_actor():
    name1 = request.args.get('name1')
    name2 = request.args.get('name2')
    age = request.args.get('age')
    j = {}
    for n in actor_list:
        if name1 != None and name1 in n:
            j[n] = actor_data[n]
        elif name2 != None and name2 in n:
            j[n] = actor_data[n]
        elif age != None and str(age) == str(actor_data[n]["age"]):
            j[n] = actor_data[n]
    if j == {}:
        return flask.make_response(jsonify({'error': 'Not found: '}), 404)
    print(j)
    return flask.make_response(jsonify(j), 200)


# http://localhost:5000/actor/update/Charlotte Rampling
# {"total_gross":199}
@app.route('/actor/update/<string:name>', methods=['PUT'])
def update_actor(name):
    if not(name in actor_list):
        return flask.make_response(jsonify({'error': 'Not found actor with name: ' + name}), 404)

    new_data = request.get_json()
    for key in new_data:
        try:
            actor_data[name][key] = new_data[key]
        except:
            return flask.make_response(jsonify({'error': 'Not found key: ' + key}), 404)
    # load new information to json file
    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)

    return jsonify({'result': name+"'s info has been updated successfully!"}, 201)


#http://localhost:5000/actor/add/name_new_1
"""
{
    "age": 73,
    "json_class": "Actor",
    "movies": [],
    "name": "name_new_1",
    "total_gross": 0
}
"""
@app.route('/actor/add/<string:name>', methods=['POST'])
def add_actor(name):
    actor_list = [*data[0]]
    if name in actor_list:
        return flask.make_response(jsonify({'error': 'Actor '+name+' is already in the data file!'}), 404)
    new_data = request.get_json()
    # add new actor to data
    data[0][name] = new_data
    # load new information to json file
    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)
    return jsonify({'result': data[0][name]}), 201


# http://localhost:5000/actor/delete/name_new_1
@app.route('/actor/delete/<string:name>', methods=['DELETE'])
def delete_actor(name):
    actor_list = [*data[0]]
    if name not in actor_list:
        return flask.make_response(jsonify({'error': 'Actor '+name+' not exist!'}), 404)
    try:
        del data[0][name]
    except:
        return flask.make_response(jsonify({'error': 'sorry could not delete!'}), 404)

    # load new information to json file
    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)

    return jsonify({'result': name+' deleted successfully!'})


# http://localhost:5000/movie/all
@app.route('/movie/all', methods=['GET'])
def get_all_movie():
    return jsonify(movie_list)


# http://localhost:5000/movie/Love Affair
@app.route('/movie/<string:movie_name>', methods=['GET'])
def get_movie_information(movie_name):
    try:
        x = movie_data[movie_name]
    except:
        return flask.make_response(jsonify({'error': 'Not found: ' + movie_name}), 404)
    return jsonify(x)


# http://localhost:5000/movie/names?name=Love
# http://localhost:5000/movie/names?name=Love&year=1999
@app.route('/movie/names', methods=['GET'])
def get_movie_by_condition():
    movies = {}
    name = request.args.get('name')
    year = request.args.get('year')
    for m in movie_list:
        if not name == None and ((name in m) or str(name) == str(m)):
            if year == None:
                movies[m] = movie_data[m]
            elif str(movie_data[m]["year"]) == str(year):
                movies[m] = movie_data[m]
    if movies == {}:
        return flask.make_response(jsonify({'error': 'Not found: '}), 404)

    return flask.make_response(jsonify({'error': movies}), 200)


# http://localhost:5000/movie?name1=Eyes&name2=Love Lies Bleeding
# http://localhost:5000/movie?year1=1999
@app.route('/movie', methods=['GET'])
def get_one_movie():
    name1 = request.args.get('name1')
    name2 = request.args.get('name2')
    year1 = request.args.get('year1')
    year2 = request.args.get('year2')
    for m in movie_list:
        if not name1 == None and (name1 in m) or name1 == m:
            return flask.make_response(jsonify({'result': movie_data[m]}), 200)

        if not name2 == None and (name2 in m) or name2 == m:
            return flask.make_response(jsonify({'result': movie_data[m]}), 200)

        if not year1 == None and str(movie_data[m]["year"]) == str(year1):
            return flask.make_response(jsonify({'result': movie_data[m]}), 200)

        if not year2 == None and str(movie_data[m]["year"]) == str(year2):
            return flask.make_response(jsonify({'result': movie_data[m]}), 200)

    return flask.make_response(jsonify({'error': 'Not found: '}), 404)


# http://localhost:5000/update/update/Death Weekend
# {"box_office":111,
#   "year":    111}
@app.route('/movie/update/<string:name>', methods=['PUT'])
def update_movie(name):
    print(name)
    print(name in movie_list)
    if not(name in movie_list):
        return flask.make_response(jsonify({'error': 'Not found movie with name: ' + name}), 404)

    new_data = request.get_json()
    for key in new_data:
        try:
            movie_data[name][key] = new_data[key]
        except:
            return flask.make_response(jsonify({'error': 'Not found key: ' + key}), 404)
    # load new information to json file
    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)

    return jsonify({'result': name+"'s info has been updated successfully!"}, 201)


# http://localhost:5000/movie/add/new_1
"""
{
        "json_class": "Movie",
        "name": "new_1",
        "wiki_page": "https://en.wikipedia.org/wiki/madeup",
        "box_office": 0,
        "year": 0,
        "actors": []
}
"""
@app.route('/movie/add/<string:name>', methods=['POST'])
def add_movie(name):
    movie_list = [*data[1]]
    if name in movie_list:
        return flask.make_response(jsonify({'error': 'Movie '+name+' is already in the data file!'}), 404)
    new_data = request.get_json()
    # add new actor to data
    data[1][name] = new_data
    movie_data = data[1]
    # load new information to json file
    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)
    return jsonify({'result': data[1][name]}, 201)


# http://localhost:5000/movie/delete/new_1
@app.route('/movie/delete/<string:name>', methods=['DELETE'])
def delete_movie(name):
    movie_list = [*data[1]]
    if name not in movie_list:
        return flask.make_response(jsonify({'error': 'Movie '+name+' not exist!'}), 404)
    try:
        del data[1][name]
    except:
        return flask.make_response(jsonify({'error': 'sorry could not delete!'}), 404)

    # load new information to json file
    with open("data.json", 'w') as f:
        json.dump(data, f, indent=4)

    return jsonify({'result': name+' deleted successfully!'})


if __name__ == '__main__':
    data = load_data("data.json")
    actor_data = data[0]
    movie_data = data[1]

    actor_list = [*actor_data]
    movie_list = [*movie_data]

    app.run(debug=True)