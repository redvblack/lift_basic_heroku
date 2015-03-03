package code.cucumber.pages

object ConsultantHomePage extends PageObject {
  protected val namedTexts = Map("login" -> "Login")

  def path = "/consultant/index.html"
}
