package code.cucumber.pages

trait PageObject {
  protected val namedTexts: Map[String, String]
  def path : String
  def text(identifier:String) = { namedTexts(identifier) }
}
