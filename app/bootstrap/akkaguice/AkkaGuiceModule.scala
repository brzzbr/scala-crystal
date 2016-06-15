package bootstrap.akkaguice

import com.google.inject.AbstractModule
import crawling.{CompetitorsPersisterActor, CrawlMasterActor}
import play.api.libs.concurrent.AkkaGuiceSupport

/**
  * Created by borisbondarenko on 15.06.16.
  */
class AkkaGuiceModule extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {
    bindActor[CrawlMasterActor]("crawl-master")
    bindActor[CompetitorsPersisterActor]("competitors-persister")
  }
}