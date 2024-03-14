package cn.soybean.example

import io.mcarle.konvert.api.Konfig
import io.mcarle.konvert.api.Konvert
import io.mcarle.konvert.api.Konverter
import io.mcarle.konvert.api.Mapping
import io.mcarle.konvert.api.config.KONVERTER_GENERATE_CLASS
import io.mcarle.konvert.api.converter.LONG_TO_UINT_CONVERTER
import io.mcarle.konvert.api.converter.STRING_TO_INT_CONVERTER
import io.mcarle.konvert.injector.cdi.KApplicationScoped

@Konverter(
    options = [
        /**
         * This is already set in the pom.xml, but you can also define it here (and override default and global values with that).
         */
        Konfig(key = KONVERTER_GENERATE_CLASS, value = "true")
    ]
)
@KApplicationScoped
interface Mapper {
    @Konvert(
        mappings = [
            Mapping(source = "streetName", target = "street"),
            Mapping(source = "zipCode", target = "zip")
        ],
        priority = 1
    )
    fun fromDto(dto: AddressDto): Address

    @Konvert(
        mappings = [
            Mapping(source = "street", target = "streetName"),
            Mapping(source = "zip", target = "zipCode"),
            Mapping(source = "streetNumber", target = "streetNumber", enable = [STRING_TO_INT_CONVERTER])
        ]
    )
    fun toDto(address: Address): AddressDto

    @Konvert(
        mappings = [Mapping(source = "numberOfYearsSinceBirth", target = "age")]
    )
    fun fromDto(dto: PersonDto): Person

    @Konvert(
        mappings = [Mapping(
            source = "age",
            target = "numberOfYearsSinceBirth",
            enable = [LONG_TO_UINT_CONVERTER]
        )]
    )
    fun toDto(person: Person): PersonDto
}
