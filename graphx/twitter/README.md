## Lab n.n : GraphX

### OverView
We will be constructing graphs and doing basic operations on them 

### Depends On 
None

### Run time
20 mins


## STEP 1: Login to your Spark node, start Spark shell
Instructor will provide details

## Step 2: Build the graph from vertices and edges, setting the data in the code
 
All of the following steps are performed by entering the commands in the Spark Scala shell

#### Import the necessary libraries
 
     import org.apache.spark.graphx._
     import org.apache.spark.rdd.RDD
 
#### Construct the array of vertices
 
 Data structure: twitter handle, number of followers, gender of the tweeter

     val vertexArray = Array(
        (1L, ("@markkerzner", 309, "M")),
        (2L, ("@mjbrender", 3101, "M")),
        (3L, ("@dridisahar1", 27, "F")),
        (4L, ("@dez_blanchfield ", 38600, "M")),
        (5L, ("@ch_doig ", 519, "F")),
        (6L, ("@Sunitha_Packt ", 332, "F")),
        (7L, ("@WibiData ", 2477, "M"))
     )
          
####  Construct the array of edges

On this step, these are all my followers, so they connect to me
     
    val edgeArray = Array(
        Edge(1L, 2L, 7),
        Edge(1L, 3L, 2),
        Edge(1L, 4L, 4),
        Edge(1L, 5L, 3),
        Edge(1L, 6L, 1),
        Edge(1L, 7L, 2)
)
 
#### Prepare the data for Spark processing. What do ".parallelize" methods below accomplish?
 
     val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(vertexArray)
     val edgeRDD: RDD[Edge[Int]] = sc.parallelize(???)
 
#### Construct the graph from the vertices and edges
 
     val graph: Graph[(String, Int), Int] = Graph(???, ???)
 
#### Print all my followers and their gender

    graph.vertices.collect.foreach { case (id, (name, nFollow, gender)) => 
        println(s"Tweeter $name has $nFollow followers and is $gender") }

#### Filter out male followers
 
    graph.vertices.filter { case (id, (name, followers, gender)) => gender != "M" }.collect.
        foreach { case (id, (name, followers, gender)) => 
        println(s"$name should be a female with $followers followers") }

      
#### Count my significant followers
      
    graph.vertices.filter { case (id, (name, nFollow, gender)) => nFollow > 1000 }.count        

#### Count those followers who do enough re-tweeets for me

    graph.edges.filter { case (edge) => edge.attr > 5 }.count
    
#### Count my male and female followers
    
    graph.vertices.filter { case (id, (name, nFollow, gender)) => gender == "M" }.count
    graph.vertices.filter { case (id, (name, nFollow, gender)) => gender == "F" }.count

## Bonus: Fix broken links 

#### Use the filtered graph and remove the broken links - those for which the vertices were filtered out
