package crawling

import akka.actor.{Actor, PoisonPill, Props}
import models.{Competitor, Good}
import org.joda.time.{DateTime, LocalDate}
import org.jsoup.Jsoup
import play.api.libs.ws.WSResponse

/**
  * Created by borisbondarenko on 18.06.16.
  */
object GoodsAnalizerActor {

  trait Factory {
    def apply(): Actor
  }

  def props = Props[GoodsAnalizerActor]

  case class AnalizeGoods(competitor: Competitor, goods: WSResponse)
  case class AnalizeGoodsComplete(competitor: Competitor, goods: Seq[Good])
}

class GoodsAnalizerActor extends Actor {

  import GoodsAnalizerActor._
  import scala.collection.JavaConversions._

  override def receive: Receive = {
    case AnalizeGoods(cmp, goods) =>
      val g = Jsoup.parse(goods.bodyAsUTF8)
      val res = for (el <- g.select("div.b-item.b-item-hover")) yield {
        val id = el.attr("id").replaceAll("[^\\d.]", "").toLong                  // id
        val name = el.select("div.title > a").text                               // name
        val price = el.select(".price").text.replaceAll("[^\\d.]", "")           // price
        val imgUrl = el.select("img").attr("src")                                // img url
        val itemUrl = "https://www.livemaster.ru/" + el.select("a").attr("href") // item url

        Good(
          None,
          cmp.id,
          id, name,
          if (price.isEmpty) 0 else price.toDouble,
          imgUrl,
          itemUrl,
          LocalDate.now)
      }

      sender ! AnalizeGoodsComplete(cmp, res)
      self ! PoisonPill
  }
}
