[![Build Status](https://travis-ci.org/Lozitsky/voting.svg?branch=master)](https://travis-ci.org/Lozitsky/voting)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d7874e7f975c4b82ad5f3aa5a605d823)](https://www.codacy.com/app/Lozitsky/voting?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Lozitsky/voting&amp;utm_campaign=Badge_Grade)

<h1> #private voting system </h1>
Maven Spring Boot Security JPA(Hibernate) REST(Jackson) Tests(JUnit4)
<br>
<br>
<br>
<br>
<a href="https://voting-rest.herokuapp.com">Demo project "Voting for restaurants"</a>
<br>
<br>
<br>
<h5>Admin</h5>

<ul>
<li>can registration( For example: 
                     <a href="https://voting-rest.herokuapp.com/rest/restaurants">get all restaurants</a>  admin@gmail.com : password )</li>
<li>can add restaurants(name and description)</li>
<li>can add restaurants dishes(name and price)</li>
<li>can watch the voting history(by restaurant Id, by date)</li>
<li>can watch list of restaurants</li>
<li>can watch list of dishes</li>
<li>can change field enable(true|false) for users</li>
<li>can’t vote</li>
<li>can’t delete admins or users</li>
</ul>
<br>
<h5>User</h5>
<ul>
<li>can registration( For example: 
                     <a href="https://voting-rest.herokuapp.com/rest/user/votes">get result or list menu with restaurants</a>  user2@yandex.ru : password )</li>
<li>can vote( For example: 
                     <a href="https://voting-rest.herokuapp.com/rest/user/voteFor/10006">vote for restaurant with id 10006</a>)</li>
<li>can watch voting after the vote (if he has not voted, he will redirect to the voting list)</li>
<li>can change his voice by 11 o’clock</li>
<li>can vote between 00:00 and 23:59</li>
<li>can’t add or remove restaurants and dishes and other entities</li>
</ul>
<br>
<h5>Voting</h5>
<ul>
<li>from 00:00 to 23:59</li>
<li>the user can vote for the menu that was created or updated by the Administrator today</li>
<li>the system have a voting history by date</li>
</ul>
<br>
<h5>Menu</h5>
<ul>
<li>the system have a menu(dishes) history by date</li>
</ul>
<br>
<h5>Dish</h5>
<ul>
<li>consist of the name and price</li>
</ul>
<br>
<h4>Curl commands</h4>
For testing on localhost. If you want testing on cloud service Heroku you need change "http://localhost:8080" to "https://voting-rest.herokuapp.com"
<br>
<br>
Notes. After 30 minutes of inactivity, Heroku needs some time (~ 2 minutes) to start the system.
<ul>
<li>
<h6>Get list all thr restaurants:</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants
</li>
<li>
<h6>Get restaurant by Id:</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/10004
</li>
<li>
<h6>Get restaurants with dishes:</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants/dishes
</li>
<li>
<h6>Get restaurant with dishes by Id:</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants/dishes/10006
</li>
<li>
<h6>Get restaurant with dishes by date:</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants/dishes/date/2017-11-11
</li>
<li>
<h6>Get all the restaurants with voices</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants/votes
</li>
<li>
<h6>Get restaurant with voices by Id</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants/votes/10005
</li>
<li>
<h6>Get restaurant with voices by date</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/restaurants/votes/date/2018-01-11
</li>
<li>
<h6>Get munu(dishes) with the restaurants for voting(only for User):</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic dXNlckB1a3IubmV0OnBhc3N3b3Jk' -i http://localhost:8080/rest/user/dishes/forVoting
</li>
<li>
<h6>Create a dish by restaurant id(only for Admin):</h6>
curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10008 --data '{"name":"Some Dish3 блюдо","price":350}'
</li>
<li>
<h6>Update the dish by id(only for Admin):</h6>
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10022 --data '{"name":"New Dish блюдо","price":453}'
</li>
<li>
<h6>Delete dish by id(only for Admin)</h6>
curl -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10021
</li>
<li>
<h6>Create restaurant(only for Admin):</h6>
curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant --data '{"name":"Ресторан Golden Star","description":"text текст"}'
</li>
<li>
<h6>Update restaurant by Id(only for Admin):</h6>
curl -X PUT -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/10007 --data '{"name":"Ресторан NEW Star","description":"new текст"}'
</li>
<li>
<h6>Delete Restaurant(only Admin):</h6>
curl -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/10008
</li>
<li>
<h6>Get user by id(only for Admin)</h6>
curl -X GET -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/user/10002
</li>
<li>
<h6>Get all users(only for Admin)</h6>
curl -X GET -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/users
</li>
<li>
<h6>Set the user field with the name enabled to true by id(only for Admin)</h6>
curl -X PUT -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/user/on/10003
</li>
<li>
<h6>Set the user field with the name enabled to false by id(only for Admin)</h6>
curl -X PUT -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/user/off/10003
</li>
<li>
<h6>Get results voting or redirect when you don't vote(only for User)</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic dXNlckB1a3IubmV0OnBhc3N3b3Jk' -i http://localhost:8080/rest/user/votes
</li>
<li>
<h6>Vote for a restaurant by restaurant Id(only for User and only one time if more than 11:00)</h6>
curl -X GET -H 'Content-Type: application/json' -H 'Authorization: Basic dXNlckB1a3IubmV0OnBhc3N3b3Jk' -i http://localhost:8080/rest/user/voteFor/10006
<h6></h6>
</li>
</ul>

For testing on Firefox can try <a href="http://restclient.net/"> RESTClient</a>
<br>
<br>
<footer>10.2018
  <p>&copy; Created by <a href="https://github.com/Lozitsky" target="_blank">Kirilo Lozitsky</a></p>
  <a href="mailto:kirilojava@ukr.net">Email for questions</a> 
</footer>