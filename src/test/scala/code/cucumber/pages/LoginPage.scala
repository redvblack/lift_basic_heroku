package code.cucumber.pages

object LoginPage extends PageObject {
  protected val namedTexts = Map("login" -> "Login")

  def path = "/user_mgt/login"
}
