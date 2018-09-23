[![Build Status](https://travis-ci.org/Lozitsky/voting.svg?branch=master)](https://travis-ci.org/Lozitsky/voting)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d7874e7f975c4b82ad5f3aa5a605d823)](https://www.codacy.com/app/Lozitsky/voting?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Lozitsky/voting&amp;utm_campaign=Badge_Grade)

<h1># private voting system</h1>
Maven Spring Boot Security JPA(Hibernate) REST(Jackson)
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
<li>Can registration</li>
<li>Can add restaurants</li>
<li>Can add dishes of restaurants</li>
<li>Can't view result voting</li>
<li>Can’t vote</li>
<li>Can’t delete admins or users</li>
</ul>
<br>
<h5>User</h5>
<ul>
<li>Can registration</li>
<li>Can vote</li>
<li>Can view result voting</li>
<li>Can change his voice by 11 o’clock</li>
<li>Can vote between 00:00 and 23:59</li>
<li>Can’t add or remove restaurants and dishes and other entities</li>
</ul>
<br>
<h5>Voting</h5>
<ul>
<li>From 00:00 to 23:59</li>
<li>User can vote for menu created or updated today by Admin</li>
<li>The system have a voting history by date</li>
</ul>
<br>
<h5>Menu</h5>
<ul>
<li>The system have a menu(dishes) history by date</li>
</ul>
<br>
<h5>Dish</h5>
<ul>
<li>consist of the name and price</li>
</ul>
<br>
<h4>
Curl commands
</h4>
<ul>
<li>
<h6>Get list all restaurants:</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurants
</li>
<li>
<h6>Get restaurant by Id:</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurant/10004
</li>
<li>
<h6>Get restaurants with dishes:</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurants/dishes
</li>
<h6>Get restaurant with dishes by Id:</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurant/dishes/10006
</li>
<li>
<h6>Get restaurant with dishes by date:</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurants/dishes/date/2018-09-19
</li>
<h6>Get munu(dishes) with restaurants for voting:</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/dishes/forVoting
</li>
<li>
<h6>Create restaurant menu by restaurant id(only for Admin):</h6>
curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/create/10094 --data '{"name":"Some Dish3","price":300}'
</li>
<h6>Update restaurant munu(dish) by dish id(only for Admin):</h6>
curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/update/10020 --data '{"name":"Some Dish3 блюдо","price":350}'
</li>
<li>
<h6>Create restaurant(only for Admin):</h6>
curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/create --data '{"name":"Ресторан Golden Star","description":"text текст"}'
</li>
<h6>Update restaurant by restaurant Id(only for Admin):</h6>
curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/update/10005 --data '{"name":"Ресторан New Star","description":"text текст"}'
</li>
<li>
<h6>Get results voting(only for User)</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/votes
</li>
<h6>Vote for a restaurant by restaurant Id(only for User)</h6>
curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/voteFor/10007
</li>
<li>
<h5></h5>
</li>
</ul>