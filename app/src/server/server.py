from flask import Flask, request
from flask_restful import Resource, Api, reqparse
import copy
import time
import json

app = Flask(__name__)
api = Api(app)
habits = {}
users = {}
dates = {}
parser = reqparse.RequestParser()
parser.add_argument("habit")
parser.add_argument("user")
parser.add_argument("habit_date")

class Habits(Resource):
    def save(self):
        save_db(habits, "habits.json")

    def get(self):
        return list(habits.values())

    def post(self):
        args = parser.parse_args()
        id = 1 if len(habits) is 0 else int(max(habits.keys())) + 1
        habit = from_json(args["habit"])
        if (not "id" in habit.keys()) or (habit["id"] <= 0):
            habit["id"] = id
        habits[habit["id"]] = habit
        self.save()
        return habit

    def delete(self):
        args = parser.parse_args()
        habit = from_json(args["habit"])
        print(habit)
        print(habit.keys())
        if habit["id"] in habits.keys():
            habits.pop(habit["id"])
        self.save()
        return habit


class Users(Resource):
    def save(self):
        save_db(users, "users.json")

    def get(self):
        return list(users.values())

    def post(self):
        args = parser.parse_args()
        user = from_json(args["user"])
        if not "email" in user.keys():
            user["email"] = ""
        if not "isAdmin" in user.keys():
            user["isAdmin"] = False
        users[user["email"]] = user
        self.save()
        return user


class Dates(Resource):
    def save(self):
        save_db(dates, "dates.json")

    def get(self):
        return list(dates.values())

    def post(self):
        args = parser.parse_args()
        habit_date = from_json(args["habit_date"])
        habit_date["habitIdDate"] = str(habit_date["habitId"]) + str(habit_date["date"])
        dates[habit_date["habitIdDate"]] = habit_date
        self.save()
        return habit_date

    def delete(self):
        args = parser.parse_args()
        habit_date = from_json(args["habit_date"])
        habit_date["habitIdDate"] = str(habit_date["habitId"]) + str(habit_date["date"])
        if habit_date["habitIdDate"] in dates.keys():
            dates.pop(habit_date["habitIdDate"])
        self.save()
        return habit_date


def load(filename):
    db_file = open(filename, "r")
    content = db_file.read()
    item_array = from_json(content)
    item_dict = {}
    for item in item_array:
        if filename == "habits.json":
            item_dict[item["id"]] = item
        elif filename == "users.json":
            item_dict[item["email"]] = item
        elif filename == "dates.json":
            item_dict[item["habitIdDate"]] = item
    db_file.close()
    log("{0} was loaded:".format(filename))
    log(item_dict)
    return item_dict

def log(message):
    print(message)

def save_db(db, filename):
    db_file = open(filename, "w")
    db_file.write(to_json(list(db.values())))
    db_file.close()

def from_json(item):
    item = item.replace("'", '"')
    return json.loads(item)

def to_json(item):
    content = json.dumps(item, sort_keys=True, indent=2, separators=(',', ': '));
    return content

if __name__ == '__main__':
    habits = load("habits.json")
    users = load("users.json")
    dates = load("dates.json")

    api.add_resource(Habits, '/habits')
    api.add_resource(Users, '/users')
    api.add_resource(Dates, '/dates')

    app.run(host="0.0.0.0", port=5000, debug=True)
