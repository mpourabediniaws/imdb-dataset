# imdb-dataset

This projects is a sample to provide services for: 
  
  1. Import the imdb dataset into the application
  2. Return all the titles in which both director and writer are the same person and he/she is still alive
  3. Get two actors and return all the titles in which both of them played at
  4. Get a genre from the user and return best titles on each year for that genre based on number of votes and rating
  5. Count how many HTTP requests you received in this application since the last startup

We can run this simple spring boot application to have following Rest api examples:

  1. http://localhost:8080/api/titles
  2. http://localhost:8080/api/titles/selected-actors?firstActor=nm0827509&secondActor=nm0532072
  3. http://localhost:8080/api/titles/best-among-genre?genre=Comedy
  4. http://localhost:8080/api/actuator/prometheus (api_call_total shows the count of rest calls)
 
 
