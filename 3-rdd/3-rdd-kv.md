[<< back to main index](../README.md) / [RDD labs](./README.md)

Lab 3.3 : Key/Value Pair RDDs
=============================
### Overview
Learn to work with 

### Depends On 
[3.2 multi RDD operations](2-rdd-multi.md)

### Run time
20-30 mins


----------------------------
Meetup Recommendation Take 2
----------------------------
In the previous lab we created datasets using `parallelize` keyword.  In this lab we are going to load key/value RDDs from files.


----------------------------
Analyzing Meetup Data
----------------------------

## Step 1 : Start Spark Shell
Change working directory to `spark-labs`.  This way, we can access data using relative paths (makes life simpler)
```
  $  cd ~/spark-labs
```

#### == Scala:
```
  $   ~/spark/bin/spark-shell
```

#### == Python
```
  $    ~/spark/bin/pyspark
```

## Step 2: Load data
The file [data/meetup/meetup.csv](../data/meetup/meetup.csv)  has following contents
```
u1,m1
u2,m2
u1,m2
u3,m1
u2,m3
u2,m4
u3,m5
u1,m3
u2,m5
u4,m6
u5,m1

```

#### == Scala:
```scala
val meetups = sc.textFile("data/meetup/meetup.csv")
```

#### == Python
```python
meetups = sc.textFile("data/twinkle/meetup.csv")
```

**Print the content of variable `meetups`**  
Hint : `collect`

`meetups` is an RDD of ` Array[String]`


## Step 3 : Creating a key-value pair RDD 
Use `map` to create pair RDD

#### == Scala:
```scala
val userMeetupsKV = meetups.map(line => {
                    val tokens = line.split(",") // split the line
                    (tokens(0), tokens(1)) // create a KV pair (user, meetup)
                    })
```

** TODO : print the contents of `userMeetups` **

** TODO: What is resulting RDD type from this? **
```scala
val x = meetups.map(line => line.split(","))
```

## Step 4 : RDD Operations

** TODO: find all meetups per user **  
Hint : `groupByKey`

** TODO: find meetups for user 'u1' **  
Hint : `filter`  
`rdd.filter{case (k,v) => k == "u1"}`

** TODO: Find number of meetups per user **  
Hint : `countByKey`

** TODO: Find all unique meetups in the dataset **  
Hint : `values` and `distinct`

** TODO:  Sort the output of above **  
Hint : `sorted`


----------------------------
More RDD Operations
----------------------------
## Step 5 : Create an RDD with meetup as key
For some operations we need to swap key and value pair.
Here is how...
#### == Scala:
```scala
// from original dataset
val meetup = sc.textFile("data/meetup/meetup.csv")
val meetupUsersKV = meetups.map(line => {
                    val tokens = line.split(",") // split the line
                    (tokens(1), tokens(0)) // (meetup, user)
                    })

```

```scala
// swapping an already existing KV RDD
val meetupUsersKV = userMeetupsKV.map{case(k,v) => (v,k)}
```

** TODO: Find all users for each meetup **

** TODO: Find number of users for each meetup **

** TODO: Sort the meetup by most popular to least **  
Hint : `sort` TODO2

----------------
Join Operations
----------------
## Step 6 : Load Users Dataset
The file [data/meetup/users.csv](../data/meetup/users.csv) contains users data in the following format
> userid, gender, languages(seperted by ;)

```
u1,M,Java;Ruby
u2,F,Scala;Python;Erlang
u3,M,Java;Scala
u4,F,C++;PHP;Go
u5,M,Scala;Python
u6,M,Go;Shell;C++
u7,F,PHP;Ruby
u8,F,Python;Erlang
u9,M,Java;C++
u10,M,Java
```


** TODO: Load the user data set**  
```scala
// ===== Scala =====
val users = sc.textFile("data/meetup/users.csv")
// create user RDD : user -> gender
val usersKV = users.map(line => {
                    val tokens = line.split(",")
                    val userName = tokens(0) // first
                    val gender = tokens(1) // second
                    val langs = tokens(2).split(";") // third, create lang array

                    // TODO : just return user and gender
                    (???, ???)
                    })
usersKV.collect
```

## Step 7 : Join User data and Meetup data
```scala
// ===== Scala =====
val joinedUsersMeetups = usersKV.join(userMeetupsKV)
joinedUsersMeetups.collect
//  Array[(String, (String, String))] = Array((u5,(M,m1)), (u3,(M,m1)), 
// ...
```

** TODO : Inspect the RDD type for `joinedUsersMeetups`**


## Step 8 : Meetups and Gender
Using `joinedUsersMeetups` RDD, create a new RDD as gender as key and meetup as value
```scala
// ===== Scala =====
// gender -> meetup
val genderMeetups = joinedUsersMeetups.map{
    case (user, (gender, meetup)) => (???, ???)  // TODO : return the correct values
    }
genderMeetups.collect
```

** TODO : Calculate how many Male / Females attend meetups (overall distribution) ** 

## Bonus Lab 
Create an RDD with meetup as key and gender as value.  
What kind of interesting facts you can find from this RDD?