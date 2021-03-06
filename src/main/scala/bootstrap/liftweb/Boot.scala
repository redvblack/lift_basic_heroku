package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._
import mapper._
import code.model._
import java.net.URI
import net.liftmodules.FoBo


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
      val vendor = Props.mode match {
        // Production, get stuff from Heroku props
        
        case Props.RunModes.Production => {

          val dbUri = new URI(System.getenv("DATABASE_URL"))

          val username = dbUri.getUserInfo().split(":")(0)
          val password = dbUri.getUserInfo().split(":")(1)
          val dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()

          new StandardDBVendor("org.postgresql.Driver"
            , dbUrl
            , Some(username)
            , Some(password))
        }
        case Props.RunModes.Development => {
          new StandardDBVendor("org.h2.Driver"
            , "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE"
            , None
            , None)
        }
      }
      
      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(util.DefaultConnectionIdentifier, vendor)
    
    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    def sitemap = SiteMap(
      Menu.i("Home") / "index" >> User.AddUserMenusAfter, // the simple way to declare a menu

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
         "Static Content")))

    def sitemapMutators = User.sitemapMutator

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(() => sitemapMutators(Site.sitemap))
    //Init the FoBo - Front-End Toolkit module, 
    //see http://liftweb.net/lift_modules for more info
    FoBo.InitParam.JQuery=FoBo.JQuery1102  
    FoBo.InitParam.ToolKit=FoBo.Bootstrap320 
    FoBo.init() 
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)
    LiftRules.noticesAutoFadeOut.default.set( (notices: NoticeType.Value) => {
        notices match {
          case NoticeType.Notice => Full((8 seconds, 4 seconds))
          case _ => Empty
        }
     }
    ) 
    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    // LiftRules.jsArtifacts = JQueryArtifacts
    // JQueryModule.InitParam.JQuery=JQueryModule.JQuery172
    // JQueryModule.init()
  object Site {
    import scala.xml._
    val divider1   = Menu("divider1") / "divider1"
    val ddLabel1   = Menu.i("UserDDLabel") / "ddlabel1"
    val home       = Menu.i("Home") / "index" 
    val userMenu   = User.AddUserMenusHere
    val static     = Menu(Loc("Static", Link(List("static"), true, "/static/index"), S.loc("StaticContent" , scala.xml.Text("Static Content")),LocGroup("lg2","topRight")))
   val twbs  = Menu(Loc("twbs", 
        ExtLink("http://getbootstrap.com/"), 
        S.loc("Bootstrap3", Text("Bootstrap3")), 
        LocGroup("lg2"),
        FoBo.TBLocInfo.LinkTargetBlank ))     
    
    
    def sitemap = SiteMap(
        home          >> LocGroup("lg1"),
        static,
        twbs,
        ddLabel1      >> LocGroup("topRight") >> PlaceHolder submenus (
            divider1  >> FoBo.TBLocInfo.Divider >> userMenu
            )
         )
  }
}
  
}
