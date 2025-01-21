# 114547

## Lab1 - Redis

### Exercise 3

- Added the commands executed in exercise 2, into the Forum.java program

### Exercise 4

- Changed the **.equals("")** condition to **.isEmpty()**

### Exercise 5

- Changed the **.equals("")** condition to **.isEmpty()**

- Removed the **ttl** and **expire** commands to ensure that outdated regists were deleted. Also, stopped using an hash to store each timestamp for each user. Instead, I made use of a single set that for each user stores the products requested, with the timestamp as the score value. This allows me to use the **zremrangeByScore** command to remove outdated regists. Simultaneously, it is possible to obtain the oldest regist with the **zrangeWithScores** command.

- For the second part of the exercise, I made the same changes. In addition, to be able to store the same quantities in a set, I added to the **quantity** variable the timestamp of the request, to ensure uniqueness. I also used the **zrangeByScore** command, which allowed me to obtain the amount of products that were requested in the allowed time interval.

- Changed all the time variables type to **Long** to ensure that overflow does not occur.

- These changes allowed the correct functioning of the program, something that was not verified beforehand.

## Lab2- MongoDB

### Exercise 2

- 4: Used the **countDocuments** function, instead of the **.count()** since the latter is deprecated.

- 19: Simplified the query - removed a **$project** clause that was unnecessary.

- 21: Simplified the query - removed a **$project** clause that was unnecessary.

- 23: Removed the unnecessary use of **$unwind** and **$group**.

- Kept two of the 5 additional queries, and added 3 different ones.


### Exercise 3

- Stopped using, for instance, the **Updates** and **Projection** (and others) classes from the **com.mongodb.client.model** package. In contrast, started using **Bson** wether to filter, sort, update or delete data.

- Also for making queries with **aggregate** in question c, I used **Bson** for unwind, group and project operations. This made the solution simpler.

- Apart from the points mentioned before, in question d I changed the **getRestWithNameCloserTo** function, making use of regex: regexQuery.append("$regex", ".*" + Pattern.quote(name) + ".*");. In the previous implementation, this function was not functional.


### Exercise 4 

- In the previous implementation, both of these questions were not functioning as intended. Instead of trying to make the solution from scratch, I opted to take an approach similar to the one used in the previous lab. I used the **deleteMany** function to delete documents that were outdated (past the timeslot) and the **countDocuments** function to count the number of documents existent.

- This implementation, also became simpler and more efficient, in comparison to the previous one. 

- In the second part of the exercise, I used an aggregation to sum the quantities of products requested by each user, in the allowed time interval. This was done using **$match** and **$group**.

### Exercise 5

- In the normal season, I delivered, by mistake, both the **numPhones.js** and the **populatePhones.js** files with the same content (the function numPhones). This was the only correction made.

### Exercise 6

- In this delivery, I implemented this exercise.

## Lab4- Neo4j

### Exercise 2

- 2: Used the **collect** function to get all the actors in a list, instead of having multiple rows for each movie.

- 4: Used the **WHERE** clause, enforcing p1.name < p2.name, to avoid- 4: Used the **WHERE** clause, enforcing p1.name < p2.name, to avoid symmetric results. symmetric results.

- 5: Used the **WHERE** clause, enforcing p1.name < p2.name, to avoid symmetric results.

- 17: Used the **UNWIND** clause, simplifying the query.

- 18: In the **WHERE** clause, changed the **p1<>p2** condition to **p1.name < p2.name** to avoid symmetric results.

### Exercise 3

- 3: Used the **collect** function to get all the actors in a list, instead of having multiple rows for each movie.

- 8: Added the **p1<>p2** condition to ensure that the same person is not compared with itself.

- 9: Added the **p1<>p2** condition to ensure that the same person is not compared with itself.

### Exercise 4

- Removed the 2 last queries, since they were not possible using the dataset I chose. Intead, I added 2 new possible queries.

