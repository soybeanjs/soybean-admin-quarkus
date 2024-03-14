package cn.soybean.example

import io.mcarle.konvert.api.Konverter
import org.junit.jupiter.api.Test

class TestGeneratedCode {

    @Test
    fun audiToCar() {
        val audi = Audi()
        val car = audi.toCar()
        assert(car.brand == Brand.AUDI)
    }

    @Test
    fun newCarToDefectCar() {
        val newCar = NewCar(12345.67)
        val defectCar = newCar.toDefectCar()
        assert(defectCar.price == null)
    }

    @Test
    fun newCarToDefectCarToRepair() {
        val newCar = NewCar(12345.67)
        val defectCarToRepair = newCar.toDefectCarToRepair()
        assert(defectCarToRepair.repairCosts == null)
    }

    @Test
    fun someCarToCarToRepair() {
        val someCar = SomeCar()
        val carToRepair = someCar.toCarToRepair()
        assert(carToRepair.defect == true)
    }

    @Test
    fun fullNameToLastName() {
        val fullName = FullName("Jane Doe")
        val lastName = fullName.toLastName()
        assert(lastName.lastName == "Doe")
    }

    @Test
    fun mapperPersonFromDto() {
        val mapper = Konverter.get<Mapper>()
        val dto = PersonDto(
            name = "Jane Doe",
            address = AddressDto(
                streetName = "Main St",
                streetNumber = 123,
                zipCode = "12345",
                city = "Anytown",
                country = "USA"
            ),
            numberOfYearsSinceBirth = 30.toUInt()
        )
        val person = mapper.fromDto(dto)
        verifyPersons(person, dto)
    }

    @Test
    fun personFromDto() {
        val dto = PersonDto(
            name = "Jane Doe",
            address = AddressDto(
                streetName = "Main St",
                streetNumber = 123,
                zipCode = "12345",
                city = "Anytown",
                country = "USA"
            ),
            numberOfYearsSinceBirth = 30.toUInt()
        )
        val person = dto.toPerson()
        verifyPersons(person, dto)
    }

    @Test
    fun mapperPersonToDto() {
        val mapper = Konverter.get<Mapper>()
        val person = Person(
            name = "Jane Doe",
            address = Address(
                street = "Main St",
                streetNumber = "123",
                zip = "12345",
                city = "Anytown",
                country = "USA"
            ),
            age = 30
        )
        val dto = mapper.toDto(person)
        verifyPersons(person, dto)
    }

    @Test
    fun personToDto() {
        val person = Person(
            name = "Jane Doe",
            address = Address(
                street = "Main St",
                streetNumber = "123",
                zip = "12345",
                city = "Anytown",
                country = "USA"
            ),
            age = 30
        )
        val dto = person.toPersonDto()
        verifyPersons(person, dto)
    }

    private fun verifyPersons(person: Person, dto: PersonDto) {
        assert(person.name == dto.name)
        assert(person.address.street == dto.address.streetName)
        assert(person.address.streetNumber == dto.address.streetNumber.toString())
        assert(person.address.zip == dto.address.zipCode)
        assert(person.address.city == dto.address.city)
        assert(person.address.country == dto.address.country)
        assert(person.age == dto.numberOfYearsSinceBirth.toLong())
    }
}
