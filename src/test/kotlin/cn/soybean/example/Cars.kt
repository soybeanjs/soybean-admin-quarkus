package cn.soybean.example

import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

@KonvertTo(
    value = Car::class, mappings = [
        Mapping(target = "brand", constant = "Brand.AUDI")
    ]
)
class Audi
data class Car(val brand: Brand)
enum class Brand { AUDI, BMW }


@KonvertTo(
    DefectCar::class, mappings = [
        Mapping(target = "price", ignore = true)
    ]
)
@KonvertTo(
    DefectCarToRepair::class, mappings = [
        Mapping(target = "repairCosts", ignore = true)
    ]
)
class NewCar(val price: Double)
class DefectCar {
    var price: Double? = null
}

class DefectCarToRepair(val repairCosts: Double?)


@KonvertTo(
    CarToRepair::class,
    mappings = [
        Mapping(target = "defect", ignore = true)
    ]
)
class SomeCar
data class CarToRepair(val defect: Boolean = true)


