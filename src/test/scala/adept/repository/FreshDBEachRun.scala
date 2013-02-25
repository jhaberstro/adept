package adept.repository

import java.sql.Connection
import org.scalatest._

trait FreshDBEachRun extends FunSpec with BeforeAndAfterEach {
  import db.driver.simple._
  import Helpers._
  import TestData._

  //FIXME: hack to leave in memory database running without having a session
  var session: Connection = null
  
  override def beforeEach = {
    session = database.createSession.conn
    Repository.init(repo)
  }
  
  override def afterEach = {
    database.withSession{ 
      import Database.threadLocalSession
      Repository.allDDLs.drop
    }
    session = null
  }
}