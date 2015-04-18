import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vector

// MT Cars Data is as follows:
//"name","mpg","cyl","disp","hp","drat","wt","qsec","vs","am","gear","carb"
//Header row does NOT appear in dataset

def parseData(vals : RDD[String]) : RDD[(String, Vector)] = {
  vals.map { s =>
    // TODO: split the data by commas
    val splitData = s.split(',')
    //TODO:  
    val numericFields = splitData.drop(1)
    val name = splitData(0)
    val doubles = numericFields.map(_.toDouble)
    val vectors = Vectors.dense(doubles)
    (splitData(0), vectors)
  }
}
//one-liner: val parsedData = data.map(s => Vectors.dense(s.split(',').drop(1).map(_.toDouble))).cache()


// Load and parse the data
val data = sc.textFile("../../data/mtcars/mtcars.csv")
val NamesandData = parseData(data)
val parsedData = NamesandData.map { case (string, vector) => vector } 

// Cluster the data into two classes using KMeans
// TODO: Pick different values of K / numclusters
val numClusters = 2 // Value of K in Kmeans
val clusters = KMeans.train(parsedData, numClusters, 20)

// Evaluate clustering by computing Within Set Sum of Squared Errors
val WSSSE = clusters.computeCost(parsedData)
println("Within Set Sum of Squared Errors = " + WSSSE)

val NamesandData = data.map(s => (s.split(',')(0), Vectors.dense(s.split(',').drop(1).map(_.toDouble)))).cache()

// Print out a list of the clusters and each point of the clusters
val groupedClusters = NamesandData.groupBy{rdd => clusters.predict(rdd._2)}.collect()

groupedClusters.foreach { println }



