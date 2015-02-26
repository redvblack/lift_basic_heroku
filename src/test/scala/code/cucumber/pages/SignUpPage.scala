package code.cucumber.pages

object SignUpPage extends PageObject {
  protected val namedTexts = Map("sign up" -> "Sign Up")

  def path = "/user_mgt/sign_up"
}
