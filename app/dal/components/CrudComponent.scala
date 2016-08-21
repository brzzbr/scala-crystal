package dal
package components

import scala.concurrent.Future

/**
  * Created by borisbondarenko on 25.05.16.
  */
trait CrudComponent extends TypedComponent { self: DalConfig =>

  import driver.api._

  trait IdColumn[Entity] extends Table[Entity] {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  }

  override type EntityTable <: IdColumn[Entity]

  def getAll: Future[Seq[Entity]] =
    db.run(table.sortBy(_.id).result)

  def getById(id: Long): Future[Option[Entity]] =
    db.run(table.filter(_.id === id).result.headOption)

  def count: Future[Int] =
    db.run(table.map(_.id).length.result)

  def insert(entity: Entity): Future[Unit] =
    db.run(table += entity).map(_ => ())

  def insert(entities: Seq[Entity]): Future[Unit] =
    db.run(table ++= entities).map(_ => ())

  def update(entity: Entity): Future[Unit] =
    db.run(table.insertOrUpdate(entity)).map(_ => ())

  def delete(id: Long): Future[Unit] =
    db.run(table.filter(_.id === id).delete).map(_ => ())

  def contains(entity: Entity): Future[Boolean]
}