package cn.soybean.example

import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping

@KonvertTo(
    LastName::class, mappings = [
        Mapping(target = "lastName", expression = "it.fullName?.split(\" \")?.last()")
    ]
)
data class FullName(val fullName: String?)
data class LastName(val lastName: String?)
