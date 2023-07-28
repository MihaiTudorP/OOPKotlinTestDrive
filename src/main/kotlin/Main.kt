import java.lang.IllegalArgumentException

fun main(args: Array<String>) {
    val mikesCar = Car()
    mikesCar.brand = "Toyota"
    mikesCar.model = "Prius 2023"
    mikesCar.color = CarColor.BLUE
    mikesCar.doors = 5

    mikesCar.display()

    mikesCar.move()
    mikesCar.stop()

    val nikosCar = Car()
    nikosCar.brand = "Jeep"
    nikosCar.model = "Wrangler"
    nikosCar.color = CarColor.RED
    nikosCar.doors = 3
    nikosCar.display()

    val user = User("Michael", "Jackson", 50)
    println(user)
    println(User())
    println(User('F'))

    user.favoriteCar = mikesCar
    println("${user.favoriteCar}")

    println("5 + 20 = ${Calculator.sum(5, 20)}")
    println("Int MaxValue: ${Calculator.MAX_VALUE}")

    val databaseInstance = DbConnector.getInstance()

    println(databaseInstance.toString())
    println(ExtSystemAccessor)

    val userMike by lazy { //lazy -late initialization
        println("Creating the user...")
        User("Mike", "Johnson", 34)
    }

    println("Bla bla bla")

    println(userMike)

    val carColor = CarColor.WHITE

    when(carColor){
        CarColor.BLUE -> println("Good choice, a color fit for the rich")
        CarColor.SILVER -> println("Wise choice, good for your budget")
        CarColor.WHITE -> println("Interesting choice, nice and refined")
        CarColor.RED -> println("The best choice, a color fit for a king")
        else -> throw IllegalArgumentException("Invalid color entered")
    }

    println("${carColor.colorName} : ${carColor.cost}$")

    val listView = ListView(arrayOf("Mike", "John", "Isabella", "Michelle"))
    listView.ListViewItem().displayItem(2)
}

enum class CarColor(var colorName: String, var cost: Double){ //similar to Java, but shorter code
    SILVER("Silver", 100.0),
    WHITE("White", 101.5),
    BLUE("Blue", 120.5),
    RED("Red", 359.99)
}

class Car(
    var brand: String = "MyBrand",
    var model: String = "Saloon",
    var color: CarColor = CarColor.SILVER,
    var doors: Int = 4
) {

    fun move() {
        println("The car ${propertiesInline()} is moving")
    }

    private fun propertiesInline() = "${this.brand} ${this.model} ${this.color}"

    fun stop() {
        println("The car ${propertiesInline()} has stopped")
    }

    fun display() {
        println(
            """
        Brand: ${this.brand}
        Model: ${this.model}
        Color: ${this.color}
        Doors: ${this.doors}
    """.trimIndent()
        )


    }

    override fun toString(): String {
        return "Car(brand='$brand', model='$model', color='$color', doors=$doors)"
    }
}

class User(firstName: String, lastName: String, age: Int) { //Kotlin allows also late initialization

    var firstName = firstName
    var lastName = lastName
    var age = age
    lateinit var favoriteCar: Car

    init {
        val nameRegex = Regex("[a-zA-z-]{2,}")
        if (!nameRegex.matches(firstName) || !nameRegex.matches(lastName)) {
            throw IllegalArgumentException("Wrong characters for name")
        }

        normalizeName(firstName)
        normalizeName(lastName)

        if (age < 0) {
            throw IllegalArgumentException("The age cannot be negative")
        }
    }

    constructor(sex: Char) : this((if (sex == 'M') "John" else "Jane"), "Doe", 40) {
        println("Used 2nd constructor")
    }

    constructor() : this('M') {
        println("Used 3rd constructor")
    }

    private fun normalizeName(firstName: String) {
        firstName.lowercase().replaceFirstChar { c -> c.uppercase() }

    }

    override fun toString(): String {
        return "User(firstName='$firstName', lastName='$lastName', age=$age)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        return age == other.age
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + age
        return result
    }
}

class Calculator() {
    companion object { //like static members in Java
        public const val MAX_VALUE = Int.MAX_VALUE
        fun sum(a: Int, b: Int): Int {
            return a + b
        }
    }
}

//singleton

class DbConnector private constructor() {
    private val dbProvider: String = "PSQL"

    companion object {
        private var instance: DbConnector? = null

        fun getInstance(): DbConnector {
            println("Retrieving object instance...")
            if (instance == null) {
                println("Instance not found, initializing...")
                instance = DbConnector()
                println("Instance created.")
            }
            println("Instance retrieved.")
            return instance!!
        }
    }

    override fun toString(): String {
        return "Database(databaseProvider='$dbProvider')"
    }
}

object ExtSystemAccessor {
    private const val extSystemName = "DefaultExtSystem"

    init {
        println("ExtSystem created")
    }

    override fun toString(): String {
        return "SystemName: $extSystemName"
    }
}

class ListView(val items: Array<String>){
    inner class ListViewItem(){
        fun displayItem(position: Int){
            println(items[position])
        }
    }
}