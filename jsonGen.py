import random
import json

names = ["Arturo", "Jose", "Jesus", "Kevin", "Enrique", "Omar", "Carlos", "Ana", "Guadalupe","Estrella",
"Roberto", "Sofia", "Kim", "Brie", "Angeles", "Celia", "Cecilia"]
lastNames = ["Gonzalez", "Camacho", "Ramos", "Fuentes", "Martinez", "Penaloza", "Sanches", "Mendoza", "Ortega",
"Olvera"]

genres = [ "Aerospace", "Biography", "Buddy Cop", "Burlesque","Chick Flick", "Circus", "Comedy", " Coming-of-age",
"Courtroom drama", "Disaster", "Educational" ]

first = ("Super", "Retarded", "Great", "Sexy", "Vegan", "Brave", "Shy", "Cool", "Poor", "Rich", "Fast", 
"Gummy", "Yummy", "Masked", "Unusual", "American", "Bisexual", "MLG", "Mlg", "lil", "Lil")
second = ("Coder", "Vegan", "Man", "Hacker", "Horse", "Bear", "Goat", "Goblin", "Learner", "Killer",
"Woman", "Programmer", "Spy", "Stalker", "Spooderman", "Carrot", "Goat", "Quickscoper", "Quickscoper")
third = ["Abroad", "Home Alone", "Alone", "Last Try", "Begins", "Rises", "Homecoming", "at Last"]
name = (random.choice(first) + " " + random.choice(second) + " " + random.choice(third))

jsonFile = []
for i in range (50):
  obj = {
    "id" : 100000000001+i,
    "title" : (random.choice(first) + " " + random.choice(second) + " " + random.choice(third)),
    "genre" : random.choice(genres),
    "length" : int( random.uniform(15.0,120.0) ),
    "director" : random.choice(names)+ " " +random.choice(lastNames),
    "year" : int( random.uniform(1980.0,2020.0) ),
    "price" : round(random.uniform(19.99, 999.99),2),
    "imagen" : "AABB00"+str((i+1 if i<49 else 52))+".jpg"
  }
  jsonFile.append(obj)
print(json.dumps(jsonFile,indent=4))
