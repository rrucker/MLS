import io.circe._,io.circe.generic.auto._,io.circe.parser._,io.circe.syntax._
import org.apache.spark.sql._
import org.apache.spark._
//import com.couchbase.client.core.cnc.LoggingEventConsumer.Logger
import java.util.UUID
import com.couchbase.client.scala.Cluster
import com.couchbase.client.scala.kv.ReplaceOptions
import com.couchbase.client.core.error._
import com.couchbase.client.scala._
import com.couchbase.client.scala.durability._
//import com.couchbase.client.scala.implicits.Codec
//import com.couchbase.client.scala.json._
import com.couchbase.client.scala.kv.{GetOptions, InsertOptions, MutationResult, ReplaceOptions}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import scala.collection.mutable.HashMap

import org.apache.log4j.{Level, Logger}
object Couch extends App{
  type D= Double ; type B = Boolean; type S = String
  Logger.getLogger("org").setLevel(Level.OFF)
  println("CouchCirceSDK422NEW")
case class Adr(Street : String)
case class MyClass(id : Int, name : String, adr : Seq[Adr])
val myClass = MyClass(42, "Yoyo", Seq(Adr("123"), Adr("456")))
val json1 = myClass.asJson
// println(s"json1  $json1 ")
val result = json1.as[MyClass]
 // println(s" json1.as[MyClass]   $result hhhyt        ")
val result2 = result match {
  case Right(cc) => cc
  case Left(ex) => ex
}// end match
  val instances = List(BooleanInstance(
    Set (BooleanFeature("food",true),
      BooleanFeature("goOut",true),
      BooleanFeature("cubs",true)),
    BooleanLabel("match", true)))
  println("s  instances $instances")
  val trueInstances = instances.filter(i => i.label.value)
  println(s"   true instances $trueInstances")
  val probabilityTrue = trueInstances.size.toDouble / instances.size
println(s"  probabilityTrue  $probabilityTrue ")
  val featureTypes = instances.flatMap(i => i.features.map(f => f.name)).toSet
println(s" featureTypes $featureTypes ")
  val featureProbabilities = featureTypes.toList.map {
    featureType =>
      trueInstances.map { i =>
        i.features.filter { f =>
          f.name equals featureType
        }.count {
          f => f.value
        }
      }.sum.toDouble / trueInstances.size
  }
  println(s"featureProbabiities $featureProbabilities ")
  val numerator = featureProbabilities.reduceLeft(_ * _) * probabilityTrue
println(s" numerator $numerator")
  trait FeatureType[V] {
    val name: String
  }
  trait Feature[V] extends FeatureType[V] {
    val value: V
  }
  trait Label[V] extends Feature[V]

  case class BooleanFeature(name: String, value: Boolean) extends Feature[Boolean]

  case class BooleanLabel(name: String, value: Boolean) extends Label[Boolean]

  case class BooleanInstance(features: Set[BooleanFeature], label: BooleanLabel)

  class NaiveBayesModel(instances: List[BooleanInstance]) {

    val trueInstances = instances.filter(i => i.label.value)
    val probabilityTrue = trueInstances.size.toDouble / instances.size

    val featureTypes = instances.flatMap(i => i.features.map(f => f.name)).toSet

    val featureProbabilities = featureTypes.toList.map {
      featureType =>
        trueInstances.map { i =>
          i.features.filter { f =>
            f.name equals featureType
          }.count {
            f => f.value
          }
        }.sum.toDouble / trueInstances.size
    }

    val numerator = featureProbabilities.reduceLeft(_ * _) * probabilityTrue

    def probabilityFeatureVector(features: Set[BooleanFeature]) = {
      val matchingInstances = instances.count(i => i.features == features)
      matchingInstances.toDouble / instances.size
    }

    def predict(features: Set[BooleanFeature]) = {
      numerator / probabilityFeatureVector(features)
    }

  }// end class NaiveBayesModel
 // ***************** Testing **************
 val trainingInstances = List(BooleanInstance(
   Set (BooleanFeature("food",true),
     BooleanFeature("goOut",true),
     BooleanFeature("cubs",true)),
   BooleanLabel("match", true)),
   BooleanInstance(
     Set (BooleanFeature("food",true),
       BooleanFeature("goOut",true),
       BooleanFeature("cubs",false)),
     BooleanLabel("match", false)),
   BooleanInstance(
     Set (BooleanFeature("food",true),
       BooleanFeature("goOut",true),
       BooleanFeature("cubs",false)),
     BooleanLabel("match", false)))
  val testFeatureVector = Set(
                     BooleanFeature("food", true),
                     BooleanFeature("goOut",true),
                     BooleanFeature("cubs",false))

val model = new NaiveBayesModel(trainingInstances)
  println(s" model $model")
  val prediction = model.predict(testFeatureVector)
  println(s" predictions $prediction")



}//end object Couch
